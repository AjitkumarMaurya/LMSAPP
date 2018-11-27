package com.a4apple.lmsapp.utility;

import android.os.AsyncTask;
import android.util.Log;

import com.a4apple.lmsapp.LMSApp;

import java.util.List;

import io.paperdb.Paper;

public class CompleteCourseAsyncTask extends AsyncTask<Void,Void,Void> {
    List<AnsMatchPair> pairList;
    String courseId,userId,unitId;
    public CompleteCourseAsyncTask(String courseId,String userId,String unitId) {
        Paper.init(LMSApp.getInstance());
        pairList = Paper.book().read("unitListTotal");
        this.courseId=courseId;
        this.userId=userId;
        this.unitId=unitId;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        Log.e("unitListTotal.....",""+pairList.size());

        int j=0,k=0;
        for (int i =0;i<pairList.size();i++){

            if (pairList.get(i).getRight().equalsIgnoreCase("d")){
                j++;
                if (pairList.size()==j){

                    Log.e("unitListTotal.....","call course completed jj");

                }

            }
            else {

                if (pairList.get(i).getLeft().equalsIgnoreCase(unitId)){

                    pairList.get(i).setRight("d");

                    Log.e("unitListTotal.....","course unit updated");

                    if (pairList.get(i).getRight().equalsIgnoreCase("d")){
                        k++;
                        if (pairList.size()==k){

                           Log.e("unitListTotal.....","call course completed kk ");

                        }

                    }

                }

            }

            Log.e("unitListTotal.....",""+pairList.get(i).getLeft()+""+pairList.get(i).getRight());
        }

        return null;
    }
}
