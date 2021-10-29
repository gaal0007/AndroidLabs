package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity
{
    private ArrayList<Message> elements = new ArrayList<>();
    static Button sendButton = null;
    static Button receiveButton = null;
    static EditText msgField = null;
    static ListView myListView = null;
    static ListAdapter myListAdapter = null;
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //Is this running on a tablet?
        if(findViewById(R.id.frame) == null)
            isTablet = false;
        else
            isTablet = true;

        myListView = findViewById(R.id.myListView);
        myListAdapter = new ListAdapter();
        myListView.setAdapter(myListAdapter);
        myListView.setOnItemClickListener((p, b, pos, id) -> {

            Bundle dataToPass = new Bundle();
            if(b.findViewById(R.id.sentText) != null)
            {
                TextView sentText = findViewById(R.id.sentText);
                dataToPass.putString("content", sentText.getText().toString());
                dataToPass.putLong("id", id);
                dataToPass.putBoolean("isSent", true);
            }
            else
            {
                TextView recText = findViewById(R.id.receivedText);
                dataToPass.putString("content", recText.getText().toString());
                dataToPass.putLong("id", id);
                dataToPass.putBoolean("isSent", false);
            }

            if(isTablet)
            {
                DetailsFragment df = new DetailsFragment();
                df.setArguments(dataToPass);

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.frame, df).commit();
            }
            else
            {
                Intent goToEmptyActivity = new Intent(this, EmptyActivity.class);
                startActivity(goToEmptyActivity);
            }
        });

        msgField = findViewById(R.id.messageText);
        sendButton = findViewById(R.id.sendButton);
        sendButton.setOnClickListener(btn -> onSend());
        receiveButton = findViewById(R.id.receiveButton);
        receiveButton.setOnClickListener(btn -> onReceive());

    }

    public void onSend()
    {
        String content = msgField.getText().toString();
        Message newSentMessage = new Message(content, false);
        elements.add(newSentMessage);
        myListAdapter.notifyDataSetChanged();
        msgField.setText("");
    }

    public void onReceive()
    {
        String content = msgField.getText().toString();
        Message newReceivedMessage = new Message(content, true);
        elements.add(newReceivedMessage);
        myListAdapter.notifyDataSetChanged();
        msgField.setText("");
    }

    private class ListAdapter extends BaseAdapter
    {
        public int getCount(){ return elements.size(); }

        public Object getItem(int position){ return elements.get(position).messageText; }

        public long getItemId(int position){ return (long)position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            View newView = old;
            LayoutInflater inflater = getLayoutInflater();

            //check whether the message was sent or received
            TextView tView;
            if(elements.get(position).incoming == false)
            {
                //make a new row
                newView = inflater.inflate(R.layout.sent_message_layout, parent, false);

                //set the text for this row as sent
                tView = newView.findViewById(R.id.sentText);
            }
            else
            {
                //make a new row
                newView = inflater.inflate(R.layout.received_message_layout, parent, false);

                //set the text for this row as received
                tView = newView.findViewById(R.id.receivedText);
            }

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
            this.messageText = content;
            this.incoming = isIncoming;
        }
    }
}