package org.novaria;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int groupId;
     private String name;
    private int membersNo ;
    private List<User> members = new ArrayList<>();

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    private String description;
    private List<Group> groupList;
    private User admin;

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    private final List<String> messages = new ArrayList<>();


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getMembersNo() {
        return membersNo;
    }

    public void setMembersNo(int membersNo) {
        membersNo = groupList.size();
        this.membersNo = membersNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void joinGroup(User user) {
        if (!members.contains(user)) {
            members.add(user);
        } else {
            System.out.println(user.getName() + ", has already joined " + this.getName());
        }
    }

    public void leave(User user) {
        if (user.equals(admin)) {
            System.out.println("Admin cannot leave the group!");
            return;
        }
        if (members.remove(user)) {
            System.out.println(user.getName() + " left " + name);
        } else {
            System.out.println(user.getName() + " is not in " + name);
        }
    }

    public boolean isMember(User user) {
        return members.contains(user);
    }

    public String listGroupMembers() {
            System.out.println(membersNo);
            return members.toString() + ", Amount of memebers: " + membersNo;
     }

    public void postMessage(User user, String message) {
        if (!isMember(user)) {
            System.out.println("You must join the group to post!");
            return;
        }
        if (message == null || message.trim().isEmpty()) {
            System.out.println("Message cannot be empty!");
            return;
        }
        messages.add(user.getName() + ": " + message);
        System.out.println("Posted to " + name + ": " + message);
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
    public String deleteGroup(User user){
        if (!user.equals(admin)){
            return "Only admin(s) can delete Groups ";
        }
        groupList.remove(this);
        String autoPost = user + "has successfully deleted" + this.getName();
        this.postMessage(user, autoPost);
        return user + "has successfully deleted" + this.getName();
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
