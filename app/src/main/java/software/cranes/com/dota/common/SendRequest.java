package software.cranes.com.dota.common;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

import software.cranes.com.dota.interfa.Constant;

/**
 * Created by GiangNT - PC on 23/09/2016.
 */

public class SendRequest {
    public interface HandleResponse {
        public void onSuccess(Object data);

        public void onFail(String err);
    }

    public interface StringResponse {
        public void onSuccess(String data);

        public void onFail(String err);
    }

    public static void requestData(Context context, String url, final Map<String, String> params, final int typeMethod, final int typeResponse, final Class<?> clzz, final HandleResponse handle) {
        StringRequest mRequest = new StringRequest(typeMethod, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Object data = null;
                if (typeResponse == Constant.TYPE_RESPONSE_ARRAY) {
                    data = JsonUtil.convertArrayFromJsonString(response, clzz);
                } else if (typeResponse == Constant.TYPE_RESPONSE_OBJECT) {
                    data = JsonUtil.convertObjectFromJsonString(response, clzz);
                }
                if (data != null && handle != null) {
                    handle.onSuccess(data);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (handle != null) {
                    handle.onFail(VolleyErrorHandling.getMessage(error));
                }
            }
        }) {
            /**
             * Returns a Map of parameters to be used for a POST or PUT request.  Can throw {@link
             * AuthFailureError} as authentication may be required to provide these values.
             *
             * <p>Note that you can directly override {@link #getBody()} for custom data.</p>
             *
             * @throws AuthFailureError in the event of auth failure
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (typeMethod == Method.POST) {
                    return params;
                } else {
                    return null;
                }
            }
        };
        SingletonRequestQueue.getInstance(context).addToRequestQueue(mRequest);
    }

    public static void requestPost(Context context, String url, final Map<String, String> params, final int typeResponse, final Class<?> clzz, final HandleResponse handle) {
        StringRequest mRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Object data = null;
                if (typeResponse == Constant.TYPE_RESPONSE_ARRAY) {
                    data = JsonUtil.convertArrayFromJsonString(response, clzz);
                } else if (typeResponse == Constant.TYPE_RESPONSE_OBJECT) {
                    data = JsonUtil.convertObjectFromJsonString(response, clzz);
                }
                if (data != null && handle != null) {
                    handle.onSuccess(data);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (handle != null) {
                    handle.onFail(VolleyErrorHandling.getMessage(error));
                }
            }
        }) {
            /**
             * Returns a Map of parameters to be used for a POST or PUT request.  Can throw {@link
             * AuthFailureError} as authentication may be required to provide these values.
             *
             * <p>Note that you can directly override {@link #getBody()} for custom data.</p>
             *
             * @throws AuthFailureError in the event of auth failure
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        SingletonRequestQueue.getInstance(context).addToRequestQueue(mRequest);
    }

    public static void requestGet(Context context, String url, final int typeResponse, final Class<?> clzz, final HandleResponse handle) {
        StringRequest mRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Object data = null;
                if (typeResponse == Constant.TYPE_RESPONSE_ARRAY) {
                    data = JsonUtil.convertArrayFromJsonString(response, clzz);
                } else if (typeResponse == Constant.TYPE_RESPONSE_OBJECT) {
                    data = JsonUtil.convertObjectFromJsonString(response, clzz);
                }
                if (data != null && handle != null) {
                    handle.onSuccess(data);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (handle != null) {
                    handle.onFail(VolleyErrorHandling.getMessage(error));
                }
            }
        });
        SingletonRequestQueue.getInstance(context).addToRequestQueue(mRequest);
    }

    public static void requestJsoup(Context context, String url, int typeParseJsoup, final StringResponse handle) {
        StringRequest mRequest = new StringRequest(typeParseJsoup, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Object data = null;
                if (response != null && handle != null) {
                    handle.onSuccess(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (handle != null) {
                    handle.onFail(VolleyErrorHandling.getMessage(error));
                }
            }
        });
        SingletonRequestQueue.getInstance(context).addToRequestQueue(mRequest);
    }

    public static void requestGetJsoup(Context context, String url, final StringResponse handle) {
        StringRequest mRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Object data = null;
                if (response != null && handle != null) {
                    handle.onSuccess(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (handle != null) {
                    handle.onFail(VolleyErrorHandling.getMessage(error));
                }
            }
        });
        SingletonRequestQueue.getInstance(context).addToRequestQueue(mRequest);
    }

}
