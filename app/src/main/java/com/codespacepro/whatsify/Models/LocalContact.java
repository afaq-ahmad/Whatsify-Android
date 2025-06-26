package com.codespacepro.whatsify.Models;

public class LocalContact {
    private String name;
    private String phone;

    public LocalContact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
