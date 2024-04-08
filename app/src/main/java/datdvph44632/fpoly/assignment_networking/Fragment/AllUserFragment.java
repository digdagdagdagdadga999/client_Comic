package datdvph44632.fpoly.assignment_networking.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import datdvph44632.fpoly.assignment_networking.R;
import datdvph44632.fpoly.assignment_networking.interfaces.UserInterface;
import datdvph44632.fpoly.assignment_networking.models.User;
import datdvph44632.fpoly.assignment_networking.others.DangNhap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AllUserFragment extends Fragment {

    private TextView tvIdUsername, tvIdEmail, tvIdFullname;

    private Button btnSuaThongTin, btnXoaUser;

    String TAG = "dat";
    public static final String URL_API = "http://10.24.45.253:3000/";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_user, container, false);

        tvIdUsername = view.findViewById(R.id.tvIdUsername);
        tvIdEmail = view.findViewById(R.id.tvIdEmail);
        tvIdFullname = view.findViewById(R.id.tvIdFullname);
        btnSuaThongTin = view.findViewById(R.id.btnSuaThongTin);
        btnXoaUser = view.findViewById(R.id.btnXoaUser);

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UserInterface mInterface = mRetrofit.create(UserInterface.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User", getActivity().MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        Log.d(TAG, "Lấy ID: " + id);


        Call<User> call = mInterface.getUserByID(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Lấy user thành công  " + response.body().toString());
                    User user = response.body();
                    tvIdUsername.setText(user.getUsername());
                    tvIdEmail.setText(user.getEmail());
                    tvIdFullname.setText(user.getFullname());
                    Toast.makeText(getActivity(), "Lấy user thành công", Toast.LENGTH_LONG).show();
                } else {
                    // Handle error or show a default message
                    tvIdUsername.setText("N/A");
                    tvIdEmail.setText("N/A");
                    tvIdFullname.setText("N/A");
                    Toast.makeText(getActivity(), "Lấy user không thành công", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();// in ra danh sách các file liên quan tới lỗi
            }
        });

        // sửa user
        btnSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                View view = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.update_user, null);

                EditText edUpdateUsername, edUpdateEmail, edUpdateFullname, edUpdatePassword;
                Button btnUpdate, btnHuyUser;

                edUpdateFullname = view.findViewById(R.id.edUpdateFullname);
                edUpdateUsername = view.findViewById(R.id.edUpdateUserName);
                edUpdateEmail = view.findViewById(R.id.edUpdateEmail);
                edUpdatePassword = view.findViewById(R.id.edUpdatePassword);
                btnUpdate = view.findViewById(R.id.btnUpdateUser);
                btnHuyUser = view.findViewById(R.id.btnHuyUser);

                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("User", requireContext().MODE_PRIVATE);

                if (sharedPreferences.contains("username") && sharedPreferences.contains("password") &&
                        sharedPreferences.contains("email") && sharedPreferences.contains("fullname")) {
                    // SharedPreferences chứa dữ liệu cũ của người dùng
                    // Tiếp tục với việc hiển thị dữ liệu trong các EditText
                    String username = sharedPreferences.getString("username", "");
                    String password = sharedPreferences.getString("password", "");
                    String email = sharedPreferences.getString("email", "");
                    String fullname = sharedPreferences.getString("fullname", "");

                    // Hiển thị dữ liệu trong các EditText tương ứng
                    edUpdateUsername.setText(username);
                    edUpdatePassword.setText(password);
                    edUpdateEmail.setText(email);
                    edUpdateFullname.setText(fullname);
                } else {
                    // SharedPreferences không chứa dữ liệu cũ của người dùng
                    // Thực hiện các xử lý phù hợp, ví dụ: thông báo cho người dùng hoặc xử lý mặc định
                    Toast.makeText(getActivity(), "Dữ liệu không đúng", Toast.LENGTH_LONG).show();
                }

                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(false);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = sharedPreferences.getString("id", "");
                        String username = edUpdateUsername.getText().toString();
                        String password = edUpdatePassword.getText().toString();
                        String email = edUpdateEmail.getText().toString();
                        String fullname = edUpdateFullname.getText().toString();

                        // Kiểm tra trường username, password và fullname có trống không
                        if (username.isEmpty() || email.isEmpty() || fullname.isEmpty() || password.isEmpty()) {
                            Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                            return; // Không thực hiện đăng ký nếu có trường dữ liệu trống
                        }

                        // Kiểm tra định dạng email
                        if (!email.matches("[a-zA-Z0-9._%+-]+@gmail\\.com")) {
                            Toast.makeText(getActivity(), "Email không hợp lệ", Toast.LENGTH_LONG).show();
                            return; // Không thực hiện đăng ký nếu email không hợp lệ
                        }

                        User userUpdate = new User(username, password, email, fullname);

                        Gson gson = new GsonBuilder().setLenient().create();
                        Retrofit mRetrofit = new Retrofit.Builder()
                                .baseUrl(URL_API)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();

                        UserInterface mInterface = mRetrofit.create(UserInterface.class);

                        Call<User> call = mInterface.updateUser(id, userUpdate);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.body() != null && response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username", username);
                                    editor.putString("password", password);
                                    editor.putString("email", email);
                                    editor.putString("fullname", fullname);
                                    editor.apply();
                                    editor.commit();
                                    loadData();
                                    alertDialog.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Cập nhật thông tin Thất bại", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage());
                                t.printStackTrace();// in ra danh sách các file liên quan tới lỗi
                            }
                        });
                    }
                });

                btnHuyUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        btnXoaUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có muốn xóa người dùng này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = sharedPreferences.getString("id", "");
                        Gson gson = new GsonBuilder().setLenient().create();
                        Retrofit mRetrofit = new Retrofit.Builder()
                                .baseUrl(URL_API)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();

                        UserInterface mInterface = mRetrofit.create(UserInterface.class);
                        Call<User> call1 = mInterface.deleteUser(id);
                        call1.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Xóa tài khoản thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), DangNhap.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getActivity(), "Xóa tài khoản thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage());
                                t.printStackTrace();// in ra danh sách các file liên quan tới lỗi
                            }
                        });
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });


        return view;
    }

    private void loadData() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(URL_API)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UserInterface mInterface = mRetrofit.create(UserInterface.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("User", getActivity().MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        Call<User> call = mInterface.getUserByID(id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    tvIdUsername.setText(user.getUsername());
                    tvIdEmail.setText(user.getEmail());
                    tvIdFullname.setText(user.getFullname());
                    Toast.makeText(getActivity(), "Lấy user thành công", Toast.LENGTH_LONG).show();
                } else {
                    tvIdUsername.setText("N/A");
                    tvIdEmail.setText("N/A");
                    tvIdFullname.setText("N/A");
                    Toast.makeText(getActivity(), "Lấy user không thành công", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();// in ra danh sách các file liên quan tới lỗi
            }
        });
    }

//    private void SuaUser(){
//
//    }
//

}