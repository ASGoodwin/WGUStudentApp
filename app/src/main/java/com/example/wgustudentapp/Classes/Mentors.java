package com.example.wgustudentapp.Classes;

import java.util.Random;

public class Mentors {

    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;

    //Array to hold Mentor names
    private static String[] arrMentors = {
            "Gail Hussain",
            "Hakeem Briggs",
            "Stacie Diaz",
            "Borys Bateman",
            "Kelsea Walton",
            "Aurelia Maguire",
            "Lucian Mathis",
            "Jamila Mckee",
            "Darci Espinosa",
            "Janet Hawkins",
            "Havin Mcneil",
            "Ramone Sierra",
            "Kailan Torres",
            "Donovan Baxter",
            "Enzo Dillon",
            "Ridwan Noble",
            "Beauden Banks",
            "Aman Felix",
            "Hareem Hough",
            "Jade Callahan",
            "Francesco Gamble",
            "Lynn Wong",
            "Benny Squires",
            "Elara Charles",
            "Sorcha Farrell",
            "Bethany Hawkins",
            "Joshua Alston",
            "Judah Kramer",
            "Iram Corrigan",
            "Zakariah Bender",
            "Haroon Sinclair",
            "Inigo Sanders",
            "Haider Cassidy",
            "Pooja Key"
    };
    //Array to hold mentor phones
    private static String[] arrPhone = {
            "(872) 854-8045",
            "(546) 390-5976",
            "(788) 548-9612",
            "(480) 690-5495",
            "(463) 526-0837",
            "(968) 624-0090",
            "(600) 219-3129",
            "(638) 770-7578",
            "(910) 430-0255",
            "(822) 782-2506",
            "(772) 284-7389",
            "(704) 924-9723",
            "(815) 774-5016",
            "(763) 866-4054",
            "(231) 835-9901",
            "(352) 208-8882",
            "(802) 713-7186",
            "(298) 208-4577",
            "(951) 305-0331",
            "(492) 892-1483",
            "(264) 791-5491",
            "(816) 975-4964",
            "(664) 876-0384",
            "(565) 938-3114",
            "(505) 743-1915",
            "(206) 888-9111",
            "(306) 737-8254",
            "(571) 223-9934",
            "(677) 919-0226",
            "(728) 553-8499",
            "(773) 874-3996",
            "(591) 611-3846",
            "(373) 618-3094",
            "(459) 251-0474",
    };

    public Mentors() {

        Random r = new Random();
        int a = r.nextInt(34);

        this.mentorName = arrMentors[a];
        this.mentorPhone = arrPhone[a];
        this.mentorEmail = arrMentors[a].replaceAll("\\s", ".") + "@wgu.edu";
    }

    public String getMentorName() {
        return mentorName;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }
}
