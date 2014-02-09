package com.parkhere;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parkhere.payment.ErrorDialogFragment;
import com.parkhere.payment.PaymentForm;
import com.parkhere.payment.ProgressDialogFragment;
import com.parkhere.payment.TokenList;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

public class PaymentFragment extends Fragment {

	private View rootView;

	public static final String PUBLISHABLE_KEY = "pk_test_ZPrfjZOfRtBG80mgFFa8MUzM";

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_payment, container,
				false);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void saveCreditCard(PaymentForm form) {

//		Card card = new Card(form.getCardNumber(), form.getExpMonth(),
//				form.getExpYear(), form.getCvc());
//
//		boolean validation = card.validateCard();
//		if (validation) {
//			startProgress();
//			new Stripe().createToken(card, PUBLISHABLE_KEY,
//					new TokenCallback() {
//						public void onSuccess(Token token) {
//							getTokenList().addToList(token);
//							finishProgress();
//						}
//
//						public void onError(Exception error) {
//							handleError(error.getLocalizedMessage());
//							finishProgress();
//						}
//					});
//		} else {
//			handleError("You did not enter a valid card");
//		}
	}

	private void handleError(String error) {
		DialogFragment fragment = ErrorDialogFragment.newInstance(
				R.string.validationErrors, error);
		fragment.show(getFragmentManager(), "error");
	}

	private TokenList getTokenList() {
		return null;//(TokenList) (getFragmentManager()
				//.findFragmentById(R.id.token_list_title));
	}

}
