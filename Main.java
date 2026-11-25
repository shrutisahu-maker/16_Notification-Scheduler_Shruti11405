import java.util.*;
import java.time.LocalDateTime;
import java.io.*;

class Notification {
    String id, title, message;
    LocalDateTime time;

    Notification(String id, String title, String message,
                 LocalDateTime time) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.time = time;
    }
}

class NotificationScheduler {

    PriorityQueue<Notification> pq =
        new PriorityQueue<>(
            Comparator.comparing(n -> n.time)
        );

    Queue<Notification> readyQueue = new LinkedList<>();

    void addNotification(Notification n) {
        pq.add(n);
        System.out.println("Notification Added: " + n.title);
    }

    void processNotifications(LocalDateTime currentTime) {

        while (!pq.isEmpty() &&
               !pq.peek().time.isAfter(currentTime)) {
            readyQueue.offer(pq.poll());
        }

        if (readyQueue.isEmpty()) {
            System.out.println("No notifications due.");
        }

        while (!readyQueue.isEmpty()) {
            Notification n = readyQueue.poll();
            System.out.println("Notification Sent: " + n.title);
            saveToFile(n);
        }
    }

    void saveToFile(Notification n) {
        try (FileWriter fw =
             new FileWriter("notifications.txt", true)) {
            fw.write(n.id + " | " + n.title + " | " + n.message + "\n");
            System.out.println("Saved to File Successfully!");
        } catch (IOException e) {
            System.out.println("File Error!");
        }
    }
}

class CircularQueue {

    String[] reminders = new String[5];
    int front = 0, rear = -1, size = 0;

    void add(String reminder) {
        if (size == 5) {
            System.out.println("Circular Queue Full!");
            return;
        }

        rear = (rear + 1) % 5;
        reminders[rear] = reminder;
        size++;

        System.out.println("Reminder Added: " + reminder);
    }

    void remove() {
        if (size == 0) {
            System.out.println("Circular Queue Empty!");
            return;
        }

        System.out.println("Reminder Removed: " + reminders[front]);
        front = (front + 1) % 5;
        size--;
    }
}

public class Main {

    public static void main(String[] args) {

        System.out.println("==================================");
        System.out.println("     NOTIFICATION SCHEDULER");
        System.out.println("==================================");

        NotificationScheduler scheduler =
            new NotificationScheduler();

        LocalDateTime currentTime =
            LocalDateTime.of(2026, 11, 21, 10, 15);

        Notification n1 = new Notification(
            "N101", "Assignment Submission",
            "Submit Java Assignment",
            LocalDateTime.of(2026, 11, 21, 10, 10)
        );

        Notification n2 = new Notification(
            "N102", "Project Meeting",
            "Attend Project Meeting",
            LocalDateTime.of(2026, 11, 21, 10, 20)
        );

        Notification n3 = new Notification(
            "N103", "College Reminder",
            "Complete Practical File",
            LocalDateTime.of(2026, 11, 21, 10, 12)
        );

        System.out.println("\n1. ADDING NOTIFICATIONS");

        scheduler.addNotification(n1);
        scheduler.addNotification(n2);
        scheduler.addNotification(n3);

        System.out.println("\n2. PRIORITY QUEUE");
        System.out.println("Notifications arranged by time.");

        System.out.println("\n3. GREEDY SCHEDULING");
        System.out.println("Current Time: " + currentTime);

        scheduler.processNotifications(currentTime);

        System.out.println("\n4. CIRCULAR QUEUE");

        CircularQueue cq = new CircularQueue();

        cq.add("Morning Reminder");
        cq.add("Study Reminder");
        cq.add("Assignment Reminder");

        cq.remove();

        System.out.println("\n5. FILE HANDLING");
        System.out.println("Notifications stored in notifications.txt");

        System.out.println("\n==================================");
        System.out.println("       PROGRAM COMPLETED");
        System.out.println("==================================");
    }
}