package cn.demo.protocal.response;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RestResponse<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5048859051902596391L;

	private int code;
	
	private String message;
	
	private T data;
	

	public static <T> RestResponse<T> success(){
		return success(null);
	}

	public static <T> RestResponse<T> success(T data) {
		RestResponse<T> result = new RestResponse<T>();
		result.code = HttpStatus.OK.value();
		result.message = "";
		result.data = data;
		return result;
	}
	
	public boolean isSuccess() {
		return HttpStatus.OK.value() == this.code;
	}
	
	
	public static <T> RestResponse<T> error(String message){
		return error(1000,message);
	}

	public static <T> RestResponse<T> error(int code, String message) {
		RestResponse<T> result = new RestResponse<T>();
		result.code = code;
		result.message = message;
		return result;
	}
}
