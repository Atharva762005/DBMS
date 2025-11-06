// Let’s say you have:
// mongodb-driver-sync-5.1.0.jar
// bson-5.1.0.jar
// mongodb-driver-core-5.1.0.jar
// 👉 Put them all inside a lib folder:

// And App.java should be in src/com/example folder

// To compile: javac -cp "lib/*" -d out src/com/example/App.java
// To run: java -cp "out:lib/*" com.example.App

package com.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import java.util.Scanner;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
        String uri = "mongodb://te31145:te31145@10.10.8.119/te31145_db";
        MongoClient client = MongoClients.create(uri);
        MongoDatabase database = client.getDatabase("te31145_db");
        MongoCollection<Document> collection = database.getCollection("students");

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== MongoDB Database Navigation ===");
            System.out.println("1. Add Record");
            System.out.println("2. View Records");
            System.out.println("3. Update Record");
            System.out.println("4. Delete Record");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Student Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Roll No: ");
                    int roll = sc.nextInt();
                    sc.nextLine();
                    Document doc = new Document("name", name)
                            .append("roll", roll);
                    collection.insertOne(doc);
                    System.out.println("✅ Record added successfully!");
                    break;

                case 2:
                    System.out.println("\n--- All Students ---");
                    for (Document d : collection.find()) {
                        System.out.println(d.toJson());
                    }
                    break;

                case 3:
                    System.out.print("Enter Roll No to update: ");
                    int rnoToUpdate = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    collection.updateOne(Filters.eq("roll", rnoToUpdate),
                            new Document("$set", new Document("name", newName)));
                    System.out.println("✅ Record updated!");
                    break;

                case 4:
                    System.out.print("Enter Roll No to delete: ");
                    int rnoToDelete = sc.nextInt();
                    sc.nextLine();
                    collection.deleteOne(Filters.eq("roll", rnoToDelete));
                    System.out.println("✅ Record deleted!");
                    break;

                case 5:
                    System.out.println("👋 Exiting...");
                    break;

                default:
                    System.out.println("⚠️ Invalid choice. Try again.");
            }
        } while (choice != 5);

        sc.close();
        client.close();
    }
}
