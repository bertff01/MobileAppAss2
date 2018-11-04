package ict376.murdoch.edu.au.mobileappass2attempt2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAccountFragment extends Fragment {

    private Button submitButton;
    private EditText addUsernameField;
    private EditText addPasswordField;
    private EditText confirmPasswordField;
    DataManager myDb;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.createaccountfragment, container, false);

        myDb = new DataManager(getActivity().getApplicationContext());

        addUsernameField = (EditText) v.findViewById(R.id.addUsername);
        addPasswordField = (EditText) v.findViewById(R.id.addPassword);
        confirmPasswordField = (EditText) v.findViewById(R.id.confirmPassword);

        submitButton = (Button) v.findViewById(R.id.createAccountSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = addUsernameField.getText().toString();
                String password1 = addPasswordField.getText().toString();
                String password2 = confirmPasswordField.getText().toString();

                if(username.equals("")|| !(password1.matches(password2)))
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Invalid details. Username must not be empty, passwords must match.", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    myDb.addCustomer(username, password1);
                    Toast.makeText(getActivity().getApplicationContext(), "New Account Made", Toast.LENGTH_SHORT).show();
                    System.out.println("Login Successful");
                    Fragment loginFragment = new ict376.murdoch.edu.au.mobileappass2attempt2.LogInFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();

                    ft.addToBackStack(null);
                    ft.replace(R.id.fragement_container, loginFragment);
                    ft.commit();
                }
            }
        });


        return v;

    }
}


