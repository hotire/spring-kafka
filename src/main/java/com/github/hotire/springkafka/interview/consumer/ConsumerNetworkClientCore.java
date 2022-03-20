package com.github.hotire.springkafka.interview.consumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import org.apache.kafka.clients.ClientRequest;
import org.apache.kafka.clients.ClientResponse;
import org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient;
import org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient.PollCondition;
import org.apache.kafka.clients.consumer.internals.RequestFuture;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.requests.AbstractRequest;
import org.apache.kafka.common.requests.AbstractRequest.Builder;
import org.apache.kafka.common.utils.Timer;

/**
 * @see org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient
 */
public class ConsumerNetworkClientCore {

    private final UnsentRequests unsent = new UnsentRequests();
    private final ConcurrentLinkedQueue<RequestFutureCompletionHandlerCore> pendingCompletion = new ConcurrentLinkedQueue<>();

    /**
     * @see org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient#send(Node, Builder, int)
     */
    public RequestFuture<ClientResponse> send(Node node,
                                              AbstractRequest.Builder<?> requestBuilder,
                                              int requestTimeoutMs) {
        return new RequestFuture<>();
    }

    /**
     * @see org.apache.kafka.clients.consumer.internals.ConsumerNetworkClient#poll(Timer, PollCondition, boolean)
     */
    public void poll(Timer timer, PollCondition pollCondition, boolean disableWakeup) {
        firePendingCompletedRequests();
        // trySend
    }

    /**
     * @see ConsumerNetworkClient#firePendingCompletedRequests()
     */
    private void firePendingCompletedRequests() {
        for (; ; ) {
            RequestFutureCompletionHandlerCore completionHandler = pendingCompletion.poll();
            if (completionHandler == null) {
                break;
            }
        }
    }

    private static final class UnsentRequests {
        private final ConcurrentMap<Node, ConcurrentLinkedQueue<ClientRequest>> unsent;

        private UnsentRequests() {
            unsent = new ConcurrentHashMap<>();
        }

        public void put(Node node, ClientRequest request) {
            // the lock protects the put from a concurrent removal of the queue for the node
            synchronized (unsent) {
                ConcurrentLinkedQueue<ClientRequest> requests = unsent.get(node);
                if (requests == null) {
                    requests = new ConcurrentLinkedQueue<>();
                    unsent.put(node, requests);
                }
                requests.add(request);
            }
        }

        public int requestCount(Node node) {
            ConcurrentLinkedQueue<ClientRequest> requests = unsent.get(node);
            return requests == null ? 0 : requests.size();
        }

        public int requestCount() {
            int total = 0;
            for (ConcurrentLinkedQueue<ClientRequest> requests : unsent.values()) {
                total += requests.size();
            }
            return total;
        }

        public boolean hasRequests(Node node) {
            ConcurrentLinkedQueue<ClientRequest> requests = unsent.get(node);
            return requests != null && !requests.isEmpty();
        }

        public boolean hasRequests() {
            for (ConcurrentLinkedQueue<ClientRequest> requests : unsent.values()) {
                if (!requests.isEmpty()) {
                    return true;
                }
            }
            return false;
        }

        private Collection<ClientRequest> removeExpiredRequests(long now) {
            List<ClientRequest> expiredRequests = new ArrayList<>();
            for (ConcurrentLinkedQueue<ClientRequest> requests : unsent.values()) {
                Iterator<ClientRequest> requestIterator = requests.iterator();
                while (requestIterator.hasNext()) {
                    ClientRequest request = requestIterator.next();
                    long elapsedMs = Math.max(0, now - request.createdTimeMs());
                    if (elapsedMs > request.requestTimeoutMs()) {
                        expiredRequests.add(request);
                        requestIterator.remove();
                    } else {
                        break;
                    }
                }
            }
            return expiredRequests;
        }

        public void clean() {
            // the lock protects removal from a concurrent put which could otherwise mutate the
            // queue after it has been removed from the map
            synchronized (unsent) {
                Iterator<ConcurrentLinkedQueue<ClientRequest>> iterator = unsent.values().iterator();
                while (iterator.hasNext()) {
                    ConcurrentLinkedQueue<ClientRequest> requests = iterator.next();
                    if (requests.isEmpty()) {
                        iterator.remove();
                    }
                }
            }
        }

        public Collection<ClientRequest> remove(Node node) {
            // the lock protects removal from a concurrent put which could otherwise mutate the
            // queue after it has been removed from the map
            synchronized (unsent) {
                ConcurrentLinkedQueue<ClientRequest> requests = unsent.remove(node);
                return requests == null ? Collections.<ClientRequest>emptyList() : requests;
            }
        }

        public Iterator<ClientRequest> requestIterator(Node node) {
            ConcurrentLinkedQueue<ClientRequest> requests = unsent.get(node);
            return requests == null ? Collections.<ClientRequest>emptyIterator() : requests.iterator();
        }

        public Collection<Node> nodes() {
            return unsent.keySet();
        }
    }
}
