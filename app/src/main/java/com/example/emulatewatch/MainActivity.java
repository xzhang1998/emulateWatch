package com.example.emulatewatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import com.example.emulatewatch.helper.Applications;
import com.example.emulatewatch.layoutmanager.MyLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView imageRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageRecView = findViewById(R.id.imageRecView);

        final RecyclerView.LayoutManager layoutManager = new MyLayoutManager();
        imageRecView.setLayoutManager(layoutManager);

        MyAdapter myAdapter = new MyAdapter(this);
        myAdapter.setImageList(getData());

        imageRecView.setAdapter(myAdapter);

        SnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(imageRecView);
    }

    private List<Applications> getData(){
        List<Applications> imageList = new ArrayList<>();

        imageList.add(new Applications("Facebook", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Facebook_Logo_%282019%29.png/1024px-Facebook_Logo_%282019%29.png"));
        imageList.add(new Applications("Twitter", "https://w7.pngwing.com/pngs/421/879/png-transparent-twitter-logo-social-media-iphone-organization-logo-twitter-computer-network-leaf-media.png"));
        imageList.add(new Applications("Mindfulness", "https://upload.wikimedia.org/wikipedia/commons/7/70/Mindfulness_watchOS.png"));
        imageList.add(new Applications("WhatsApp", "https://w7.pngwing.com/pngs/922/489/png-transparent-whatsapp-icon-logo-whatsapp-logo-whatsapp-logo-text-trademark-grass-thumbnail.png"));
        imageList.add(new Applications("YouTube", "https://image.pngaaa.com/129/75129-middle.png"));
        imageList.add(new Applications("LinkedIn", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTFBTEEbtxIHqbiVCJbwCisOpm3mAaDhG5fb-pZ-ULLOg&s"));
        imageList.add(new Applications("TikTok", "https://www.toymakr3d.com/shop/images/logo/tiktok.png"));

        //7
        imageList.add(new Applications("Snapchat", "https://toppng.com/uploads/preview/snapchat-logo-icon-png-snapchat-logo-circle-11562922134qgu9m89jgh.png"));
        imageList.add(new Applications("Uber", "https://cdn3.iconfinder.com/data/icons/popular-services-brands-vol-2/512/uber-512.png"));
        imageList.add(new Applications("Netflix", "https://www.citypng.com/public/uploads/preview/-11594682698soxrlignre.png"));
        imageList.add(new Applications("Amazon", "https://cdn3.iconfinder.com/data/icons/popular-services-brands/512/amazon-512.png"));
        imageList.add(new Applications("WeChat", "https://cdn3.iconfinder.com/data/icons/social-messaging-ui-color-shapes-2-free/128/social-wechat-circle-512.png"));
        imageList.add(new Applications("Zoom", "https://w7.pngwing.com/pngs/339/623/png-transparent-zoom-app-hd-logo.png"));
        imageList.add(new Applications("SnapSeed", "https://cdn-icons-png.flaticon.com/512/356/356017.png"));
        imageList.add(new Applications("Grammarly", "https://assets.stickpng.com/images/5f49294168ecc70004ae7094.png"));
        imageList.add(new Applications("Airbnb", "https://cdn.freebiesupply.com/images/large/2x/airbnb-logo.png"));
        imageList.add(new Applications("Google Maps", "https://cdn-icons-png.flaticon.com/512/355/355980.png"));
        imageList.add(new Applications("Canvas","https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/Canva_icon_2021.svg/2048px-Canva_icon_2021.svg.png"));

        //18
        imageList.add(new Applications("Slack", "https://www.itprotoday.com/sites/itprotoday.com/files/appIcon_desktop.png"));
        imageList.add(new Applications("Notion", "https://assets.stickpng.com/images/62cc1585150d5de9a3dad5eb.png"));

        return imageList;
    }
}
