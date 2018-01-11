package com.thereza.sqlitemcc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.thereza.sqlitemcc.data.FormDataModel;
import com.thereza.sqlitemcc.helper.MyDBHelper;
import com.thereza.sqlitemcc.helper.Utils;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 100;
    private Toolbar toolbar;
    private EditText inputName, inputEmail, inputAge,inputPhoneNo;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutAge,inputLayoutPhone,inputLayoutImage;
    private Button btnSave,btnView;
    ImageView picImageFromGalary;
    MyDBHelper dbHelper;
    Uri selectedImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeation();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    submitForm();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        picImageFromGalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ViewDataActivity.class);
                startActivity(i);
            }
        });

        dbHelper=dbHelper.getInstance(getApplicationContext());
    }

    public void initializeation(){

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutAge = (TextInputLayout) findViewById(R.id.input_layout_age);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutImage = (TextInputLayout) findViewById(R.id.input_layout_image);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputAge = (EditText) findViewById(R.id.input_age);
        inputPhoneNo = (EditText) findViewById(R.id.input_phone);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnView = (Button) findViewById(R.id.btn_view);

        picImageFromGalary = (ImageView) findViewById(R.id.picImageView);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputAge.addTextChangedListener(new MyTextWatcher(inputAge));
        inputPhoneNo.addTextChangedListener(new MyTextWatcher(inputPhoneNo));
    }

    /**
     * Validating form
     */
    private void submitForm() throws IOException {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validatePhone()) {
            return;
        }
        if (!validateAge()) {
            return;
        }

        String s_name = inputName.getText().toString();
        String s_age = inputAge.getText().toString();
        String s_phone = inputPhoneNo.getText().toString();
        String s_email = inputEmail.getText().toString();
        InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
        byte[] inputData = Utils.getBytes(iStream);

        FormDataModel student = new FormDataModel(s_name, s_age, s_phone,s_email,inputData);

        long inserted = dbHelper.insertStudent(student);


        if (inserted >= 0) {
            Toast.makeText(getApplicationContext(), "Data inserted",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Data insertion failed...",
                    Toast.LENGTH_LONG).show();
        }


        //Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAge() {
        if (inputAge.getText().toString().trim().isEmpty()) {
            inputLayoutAge.setError(getString(R.string.err_msg_age));
            requestFocus(inputAge);
            return false;
        } else {
            inputLayoutAge.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        if (inputPhoneNo.getText().toString().trim().isEmpty()) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone));
            requestFocus(inputPhoneNo);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_phone:
                    validatePhone();
                case R.id.input_age:
                    validateAge();
                    break;
            }
        }
    }

    // Choose an image from Gallery
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                selectedImageUri = data.getData();

                if (null != selectedImageUri) {

                    picImageFromGalary.setImageURI(selectedImageUri);
                    picImageFromGalary.getLayoutParams().height = 150;
                    picImageFromGalary.getLayoutParams().width = 150;

                    /*// Reading from Database after 3 seconds just to show the message
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (loadImageFromDB()) {
                                showMessage("Image Loaded from Database...");
                            }
                        }
                    }, 3000);*/
                }

            }
        }

    }

}
