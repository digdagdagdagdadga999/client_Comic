package datdvph44632.fpoly.assignment_networking.others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import datdvph44632.fpoly.assignment_networking.R;
import datdvph44632.fpoly.assignment_networking.interfaces.UserInterface;
import datdvph44632.fpoly.assignment_networking.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DangKy extends AppCompatActivity {

    EditText edUsername, edPassword, edEmail, edFullname;

    Button btnDangKy;

    String TAG = "dat";
    public static final String URL_API = "http://10.24.45.253:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        edUsername = findViewById(R.id.edUsername);
        edPassword = findViewById(R.id.edPassword);
        edEmail = findViewById(R.id.edEmail);
        edFullname = findViewById(R.id.edFullname);
        btnDangKy = findViewById(R.id.btnDangKy);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                String email = edEmail.getText().toString();
                String fullname = edFullname.getText().toString();

                // Kiểm tra trường username, password và fullname có trống không
                if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
                    Toast.makeText(DangKy.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                    return; // Không thực hiện đăng ký nếu có trường dữ liệu trống
                }

                // Kiểm tra định dạng email
                if (!email.matches("[a-zA-Z0-9._%+-]+@gmail\\.com")) {
                    Toast.makeText(DangKy.this, "Email không hợp lệ", Toast.LENGTH_LONG).show();
                    return; // Không thực hiện đăng ký nếu email không hợp lệ
                }

                User obj = new User(username, password, email, fullname);
                dangKy(obj);

                Intent intent = new Intent(DangKy.this, DangNhap.class);
                startActivity(intent);

                edUsername.setText("");
                edPassword.setText("");
                edEmail.setText("");
                edFullname.setText("");
            }
        });

    }

    private void dangKy(User obj) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UserInterface mInterface = mRetrofit.create(UserInterface.class);
        Call<User> call = mInterface.dangkyUser(obj);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Đăng ký thành công  " + response.body().toString());
                    Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();

                } else {
                    Log.d(TAG, "onResponse: Đăng ký không add thành công");
                    Toast.makeText(DangKy.this, "Đăng ký Không thành công", Toast.LENGTH_LONG).show();
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