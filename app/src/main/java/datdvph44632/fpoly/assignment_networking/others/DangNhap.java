package datdvph44632.fpoly.assignment_networking.others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import datdvph44632.fpoly.assignment_networking.Fragment.AllUserFragment;
import datdvph44632.fpoly.assignment_networking.R;
import datdvph44632.fpoly.assignment_networking.interfaces.UserInterface;
import datdvph44632.fpoly.assignment_networking.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangNhap extends AppCompatActivity {

    EditText edLoginUsername, edLoginPassword;
    Button btnDangNhap;

    TextView tvDangKy;
    public static final String URL_API = "http://10.24.45.253:3000/";
    String TAG = "dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edLoginUsername = findViewById(R.id.edLoginUsername);
        edLoginPassword = findViewById(R.id.edLoginPassword);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        tvDangKy = findViewById(R.id.tvDangKy);

        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edLoginUsername.getText().toString();
                String password = edLoginPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(DangNhap.this, "Không được để trống", Toast.LENGTH_LONG).show();
                    return;
                }

                login(username, password);

                edLoginPassword.setText("");
                edLoginUsername.setText("");
            }
        });
    }

    private void login(String username, String password) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UserInterface mInterface = mRetrofit.create(UserInterface.class);

        User user = new User(username, password, "", "");

        Call<User> call = mInterface.dangNhapUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    String id = user.get_id(); // Lấy id từ phản hồi

                    SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id", id);
                    editor.putString("email", user.getEmail());
                    editor.putString("username", user.getUsername());
                    editor.putString("fullname", user.getFullname());
                    editor.putString("password", user.getPassword());
                    editor.commit();

                    Intent intent = new Intent(DangNhap.this, AllFragment.class);
                    startActivity(intent);
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(DangNhap.this, "Đăng nhập không thành công", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();// in ra danh sách các file liên quan tới lỗi
            }
        });
    }
}