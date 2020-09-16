package com.yunpian.captcha.demo;

import android.content.DialogInterface;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.qipeng.captcha.QPCaptcha;
import com.qipeng.captcha.QPCaptchaConfig;
import com.qipeng.captcha.QPCaptchaListener;
import com.qipeng.captcha.utils.QPUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private SeekBar alphaSeekbar;
    private SeekBar widthSeekbar;
    private SeekBar expiredSeekbar;
    private TextView widthSeekbarTv;
    private TextView alphaSeekbarTv;
    private TextView expiredSeekbarTv;
    private CheckBox showLoadingCb;
    private CheckBox langEnCb;
    private JSONObject langPackModel;

    private int MAX_PADDING = 64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showLoadingCb = findViewById(R.id.show_loading_cb);
        langEnCb = findViewById(R.id.lang_en_cb);
        alphaSeekbar = findViewById(R.id.alpha_seekbar);
        widthSeekbar = findViewById(R.id.width_seekbar);
        expiredSeekbar = findViewById(R.id.expired_seekbar);
        widthSeekbarTv = findViewById(R.id.width_seekbar_tv);
        alphaSeekbarTv = findViewById(R.id.alpha_seekbar_tv);
        expiredSeekbarTv = findViewById(R.id.expired_seekbar_tv);

        TextView versionTv = findViewById(R.id.version_tv);
        versionTv.setText(QPCaptcha.getInstance().getSDKVersion());

        alphaSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alphaSeekbarTv.setText(String.valueOf(progress / 10f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        alphaSeekbar.setProgress(5);
        widthSeekbar.setMax(MAX_PADDING);
        widthSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar.getProgress() > MAX_PADDING) {
                    seekBar.setProgress(MAX_PADDING);
                }
                widthSeekbarTv.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() > MAX_PADDING) {
                    seekBar.setProgress(MAX_PADDING);
                }
                widthSeekbarTv.setText(String.valueOf(seekBar.getProgress()));
            }
        });
        widthSeekbar.setProgress(16);
        expiredSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                expiredSeekbarTv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        expiredSeekbar.setProgress(30);
        showLoadingCb.setChecked(true);
        langEnCb.setChecked(false);

    }

    public void startVerify(View view) {
        QPCaptcha.getInstance().init(this, "bbfe3828b1ce4c00b9d75760b435386d");
        start();
    }

    public void startVerifyWithLangPack(View view) {
        final View inputView = getLayoutInflater().inflate(R.layout.langpackmodel_input, null);
        new AlertDialog.Builder(this)
                .setTitle("自定义国际化语言")
                .setView(inputView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        QPCaptcha.getInstance().init(MainActivity.this, "bbfe3828b1ce4c00b9d75760b435386d");
                        langPackModel = new JSONObject();
                        try {
                            langPackModel.put("YPcaptcha_02", ((EditText) inputView.findViewById(R.id.YPcaptcha_02)).getText());
                            langPackModel.put("YPcaptcha_03", ((EditText) inputView.findViewById(R.id.YPcaptcha_03)).getText());
                            langPackModel.put("YPcaptcha_04", ((EditText) inputView.findViewById(R.id.YPcaptcha_04)).getText());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        start(langPackModel);
                    }
                }).show();
    }

    private void start() {
        start(null);
    }

    private void start(JSONObject langPackModel) {
        QPCaptchaConfig config = new QPCaptchaConfig.Builder(this)
                .setAlpha(alphaSeekbar.getProgress() / 10f)
                .setPaddingDp(widthSeekbar.getProgress())
                .setLangPackModel(langPackModel)
                .showLoadingView(showLoadingCb.isChecked())
                .setLang(langEnCb.isChecked() ? QPCaptchaConfig.LANG_EN : QPCaptchaConfig.LANG_ZH)
                .setCallback(new QPCaptchaListener() {
                    @Override
                    public void onLoaded() {
                        Toast.makeText(MainActivity.this, "onLoaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String msg) {
                        Toast.makeText(MainActivity.this, "onSuccess = " + msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail(String msg) {
                        Toast.makeText(MainActivity.this, "onFail msg = " + msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String msg) {
                        Toast.makeText(MainActivity.this, "onError msg = " + msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        Toast.makeText(MainActivity.this, "onReceivedSslError", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        QPCaptcha.getInstance().verify(config);
    }

}
