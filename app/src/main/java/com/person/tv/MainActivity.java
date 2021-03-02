/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.person.tv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.*;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {

    private String url;
    private View.OnClickListener onClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String oldUrl = getIntent().getStringExtra("url_set");
        url = assetsRead();
        if(url != null && url.trim().length()>0){
            changeAcitvity();
        }else{
            setContentView(R.layout.activity_main);
            if(oldUrl != null && oldUrl.trim().length()>0){
                ((EditText)findViewById(R.id.urlText)).setText(oldUrl);
            }
            final Button button = findViewById(R.id.setUrl);
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText et = findViewById(R.id.urlText);
                    url = et.getText().toString();
                    Log.i(""+url,url);
                    changeAcitvity();
                }
            };

            button.setOnClickListener(onClickListener);
        }

    }

    private void changeAcitvity(){
        Intent intent = new Intent();
        intent.setClassName("com.person.tv","com.person.tv.DetailsActivity");
        Bundle bundle  = new Bundle();
        bundle.putString("url",url);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private String assetsRead(){
        String fileName = "tv_setting.txt";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
            return bufferedReader.readLine();
        } catch (IOException e) {
            Log.e("assetsRead error",e.getMessage());
            return null;
        }
    }

}
