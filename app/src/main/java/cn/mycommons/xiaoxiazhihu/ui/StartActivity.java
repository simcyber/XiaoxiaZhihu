package cn.mycommons.xiaoxiazhihu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import cn.mycommons.xiaoxiazhihu.R;
import cn.mycommons.xiaoxiazhihu.business.callback.AdvancedSubscriber;
import cn.mycommons.xiaoxiazhihu.business.pojo.response.ext.GetStartInfoResponse;
import cn.mycommons.xiaoxiazhihu.ui.base.mvp.MvpActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_start)
public class StartActivity extends MvpActivity<StartPresenter, StartPresenter.IStartView>
        implements StartPresenter.IStartView {

    @Bind(R.id.tvText)
    TextView tvText;
    @Bind(R.id.ivImage)
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        gotoNext();
    }

    private void init() {
        presenter.doGetStartInfo()
                .subscribe(new AdvancedSubscriber<GetStartInfoResponse>() {
                    @Override
                    public void onHandleSuccess(GetStartInfoResponse response) {
                        super.onHandleSuccess(response);

                        update(response);
                    }
                });
    }

    private void gotoNext() {
        uiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getContext(), MainActivity.class));
                finish();
            }
        }, 3000);
    }

    private void update(GetStartInfoResponse response) {
        tvText.setText(response.text);

        Picasso.with(getContext())
                .load(response.img)
                .into(ivImage);
    }
}