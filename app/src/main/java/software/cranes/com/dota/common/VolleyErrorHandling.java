package software.cranes.com.dota.common;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

public class VolleyErrorHandling {
	public static String getMessage(VolleyError error) {
		String messageError = "Error";
		if (error.getClass().equals(TimeoutError.class)) {
			messageError = "Could not connect to server";
		}
		if (isServerProblem(error)) {
			messageError = "Could not connect to server";
		}

		if (isNetworkProblem(error)) {
			messageError = "Could not connect to server";
		}

		if (isConnection(error)) {
			messageError = "Please check your internet connection and try again";
		}
		return messageError;

	}

	private static boolean isNetworkProblem(VolleyError error) {
		return (error instanceof NetworkError);

	}

	private static boolean isServerProblem(VolleyError error) {
		return (error instanceof ServerError) || (error instanceof AuthFailureError);
	}

	private static boolean isConnection(VolleyError error) {
		return (error instanceof NoConnectionError);
	}

}
