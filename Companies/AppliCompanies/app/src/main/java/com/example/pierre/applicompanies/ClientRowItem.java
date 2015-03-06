package com.example.pierre.applicompanies;


public class ClientRowItem {

    private String name;
    private String first_name;
    private String username;
    private String num_client;
    private int nb_points;
    private int last_used;

    public ClientRowItem(String name, String first_name, String username, String num_client, int nb_points, int last_used) {
        this.name = name;
        this.first_name = first_name;
        this.username = username;
        this.num_client = num_client;
        this.nb_points = nb_points;
        this.last_used = last_used;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNum_client() {
        return num_client;
    }

    public void setNum_client(String num_client) {
        this.num_client = num_client;
    }

    public int getNb_points() {
        return nb_points;
    }

    public void setNb_points(int nb_points) {
        this.nb_points = nb_points;
    }

    public int getLast_used() {
        return last_used;
    }

    public void setLast_used(int last_used) {
        this.last_used = last_used;
    }
}
