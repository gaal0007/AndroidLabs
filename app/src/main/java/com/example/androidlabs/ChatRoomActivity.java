package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity
{
    private ArrayList<String> elements = new ArrayList<>();
    static Button sendButton = null;
    static Button receiveButton = null;
    static EditText msgField = null;
    static ListView myListView = null;
    static ListAdapter myListAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        myListView = findViewById(R.id.myListView);
        myListAdapter = new ListAdapter();
        myListView.setAdapter(myListAdapter);
        msgField = findViewById(R.id.messageText);
        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(btn -> onSend());
        receiveButton = findViewById(R.id.receiveButton);
        receiveButton.setOnClickListener(btn -> onReceive());
    }

    public void onSend()
    {
        //do something
    }

    public void onReceive()
    {
        //do something else
    }

    private class ListAdapter extends BaseAdapter
    {
        public int getCount(){ return elements.size(); }

        public Object getItem(int position){ return elements.get(position); }

        public long getItemId(int position){ return (long)position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            //make a new row
            if(newView == null)
            {
                newView = inflater.inflate(R.layout.activity_chat_room, parent, false);
            }

            //set the text for this row
            TextView tView = newView.findViewById(R.id.messageView);
            tView.setText(getItem(position).toString());

            return newView;
        }
    }

    private class Message
    {
        String messageText;
        Boolean incoming = false;

        Message(String content, Boolean isIncoming)
        {

        }
    }
}