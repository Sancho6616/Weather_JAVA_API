package com.azizbek.weathermap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.azizbek.weathermap.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Api api;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please Wait");

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://community-open-weather-map.p.rapidapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(Api.class);

        binding.imagesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                request(binding.edittextinputedittext.getText().toString());

            }
        });

        binding.lottieanimationviewtemp.setAnimation(R.raw.temrature);
        binding.lottieanimationviewtemp.playAnimation();
        binding.lottieanimationviewtemp.loop(true);

        binding.lottieanimationviewspeed.setAnimation(R.raw.wind);
        binding.lottieanimationviewspeed.playAnimation();
        binding.lottieanimationviewspeed.loop(true);

        binding.lottieanimationviewcity.setAnimation(R.raw.earth);
        binding.lottieanimationviewcity.playAnimation();
        binding.lottieanimationviewcity.loop(true);

    }

    public void request(String sityname){

        Call<MyWeather> call=api.getMyCityWeather(sityname);
        call.enqueue(new Callback<MyWeather>() {
            @Override
            public void onResponse(Call<MyWeather> call, Response<MyWeather> response) {

                if (response.isSuccessful()){
                    progressDialog.hide();
                    binding.linearlay.setVisibility(View.VISIBLE);


                    MyWeather myWeather=response.body();

                    float temp= (float) (myWeather.getMain().getTemp()-273f);

                    binding.textviewtemp.setText(temp+" C");
                    binding.textviewsun.setText(myWeather.getWeather().get(0).getDescription());
                    binding.textviewspeed.setText(myWeather.getWind().getSpeed()+" km/h");
                    binding.textviewcity.setText(myWeather.getSys().getCountry());

                    switch (myWeather.getWeather().get(0).getMain()+""){

                        case "Clear":
                            binding.lottieanimationviewsun.setAnimation(R.raw.sun);
                            binding.lottieanimationviewsun.playAnimation();
                            binding.lottieanimationviewsun.loop(true);
                            break;

                        case "Snow":
                            binding.lottieanimationviewsun.setAnimation(R.raw.snow);
                            binding.lottieanimationviewsun.playAnimation();
                            binding.lottieanimationviewsun.loop(true);
                            break;

                        case "Clouds":
                            binding.lottieanimationviewsun.setAnimation(R.raw.rain);
                            binding.lottieanimationviewsun.playAnimation();
                            binding.lottieanimationviewsun.loop(true);
                            break;

                    }

                    Toast.makeText(MainActivity.this, "Успешно!", Toast.LENGTH_SHORT).show();

                }else{
                    progressDialog.hide();
                    binding.edittextinputedittext.setError("Not Found!");
                    binding.linearlay.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void onFailure(Call<MyWeather> call, Throwable t) {

            }
        });

    }

}