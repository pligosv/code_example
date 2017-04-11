package com.openlife.view.fragment.authorization;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.openlife.R;
import com.openlife.core.transit.FragmentAction;
import com.openlife.core.utils.TimeUtils;
import com.openlife.presenter.authorize.AuthPhonePresenter;
import com.openlife.presenter.contract.AuthPhoneContract;
import com.openlife.view.annotation.Layout;
import com.openlife.view.annotation.Presenter;
import com.openlife.view.fragment.AbstractFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.GONE;

@Layout(R.layout.fragment_auth_phone)
@Presenter(AuthPhonePresenter.class)
public class AuthPhoneFragment extends AbstractFragment<AuthPhonePresenter>
        implements AuthPhoneContract.View {

    @BindView(R.id.til_phone_phone)
    TextInputLayout tilPhone;
    @BindView(R.id.tv_phone_hint)
    TextView tvHint;
    @BindView(R.id.et_phone_phone)
    EditText etPhone;
    @BindView(R.id.btn_phone_confirm)
    Button btnConfirm;

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.authorization_title);
    }

    @Override
    protected int getNavigationIconResId() {
        return R.drawable.ic_navigation_back_white;
    }

    @OnClick(R.id.btn_phone_confirm)
    protected void onConfirmClicked() {
        tilPhone.setErrorEnabled(false);
        getPresenter().onConfirmClicked(etPhone.getText().toString().trim());
    }

    @Override
    public void showPhoneError(int stringId) {
        tilPhone.setError(getString(stringId));
    }

    @Override
    public void showErrorDialog(int stringId) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.error_user_not_found_title)
                .setMessage(stringId)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    @Override
    public void blockUI(int stringId, int errorsNumber) {
        btnConfirm.setEnabled(false);
        etPhone.setEnabled(false);
        etPhone.setText("");
        tvHint.setText(getString(stringId, errorsNumber));
        getPresenter().startCountdownBlockUI();
    }

    @Override
    public void unblockUI() {
        btnConfirm.setEnabled(true);
        etPhone.setEnabled(true);
        tvHint.setText(R.string.phone_hint);
    }

    @Override
    public void tickBlockUI(long millisUntilFinished) {
        btnConfirm.setText(getString(R.string.authorization_reloaded_button,
                TimeUtils.getTimeFromMillis(millisUntilFinished)));
    }

    @Override
    public void finishBlockUI() {
        btnConfirm.setText(R.string.phone_edit_button_signin);
    }

    @Override
    public void onSuccessfulConfirm() {
        getTransitManager().switchBy(FragmentAction.AUTHORIZE_WITH_PHONE);
    }

    @Override
    public void onAlreadyRegistered() {
        getTransitManager().switchBy(FragmentAction.LOGIN);
    }
}
