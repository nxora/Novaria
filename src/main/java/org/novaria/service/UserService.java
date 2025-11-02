package org.novaria.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.novaria.Model.User;
import org.novaria.server.MongoDBConnection;

public class UserService {

    private final MongoCollection<Document> userCollection;

    public UserService() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        userCollection = database.getCollection("users"); // collection name
    }

    // 👇 We'll implement this fully
    public boolean registerUser(User user) {
        try {
            // Convert User object to Document
            Document userDoc = user.toDocument();

            // Check if email already exists
            Document existingUser = userCollection.find(new Document("email", userDoc.getString("email"))).first();
            if (existingUser != null) {
                System.out.println("❌ Email already in use");
                return false;
            }

            // Insert new user into MongoDB
            userCollection.insertOne(userDoc);
            System.out.println("✅ User registered successfully: " + user.getName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Failed to register user: " + e.getMessage());
            return false;
        }
    }

    public boolean validateLogin(String email, String password) {
        try {
            // Step 1: Create a query with both email and password
            Document query = new Document("email", email)
                    .append("password", password);

            // Step 2: Try to find the user
            Document existingUser = userCollection.find(query).first();

            if (existingUser != null) {
                System.out.println("✅ Login successful for: " + email);
                return true;
            } else {
                System.out.println("❌ Invalid email or password");
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to validate login: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean findUserByEmail(String email){
        try {
            Document query = new Document("email",email);
            Document existingUser = userCollection.find(query).first();

            if (existingUser != null) {
                System.out.println("✅ User found: " + existingUser.getString("email"));
                return true;
            } else {
                System.out.println("❌ User not found for email: " + email);
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌ Failed to register user: " + e.getMessage());
             e.printStackTrace();

        }
        return false;
    }

}
