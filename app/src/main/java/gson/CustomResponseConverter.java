package gson;

import com.google.gson.TypeAdapter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by zhaibingjie on 16/8/4.
 */
public class CustomResponseConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;

    CustomResponseConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override public T convert(ResponseBody value) throws IOException {
        /*Reader reader = value.charStream();
        try {
            return adapter.fromJson(reader);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }*/

        //以下为重写的部分
        try {
            String body = value.string();

            JSONObject json = new JSONObject(body);

            int count = json.optInt("count");
            String error = json.optString("error", "");

            if (error != null && error.length() == 0) {
                if (json.has("topuser")) {
                    Object data = json.get("topuser");

                    body = data.toString();

                    return adapter.fromJson(body);
                } else {
                    return (T) error;
                }
            } else {
                throw new RuntimeException(error);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            value.close();
        }
    }
}
