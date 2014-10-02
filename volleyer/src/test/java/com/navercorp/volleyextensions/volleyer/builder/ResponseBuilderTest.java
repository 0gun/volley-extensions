package com.navercorp.volleyextensions.volleyer.builder;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.navercorp.volleyextensions.volleyer.VolleyerConfiguration;
import com.navercorp.volleyextensions.volleyer.factory.DefaultVolleyerConfigurationFactory;
import com.navercorp.volleyextensions.volleyer.http.HttpContent;
import com.navercorp.volleyextensions.volleyer.http.HttpMethod;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class ResponseBuilderTest {
	RequestQueue requestQueue = mock(RequestQueue.class);

	@Test(expected=NullPointerException.class)
	public void responseBuilderConstructorShouldThrowNpeWhenVolleyerConfigurationIsNull() {
		// Given
		String url = "http://test";
		HttpMethod method = HttpMethod.GET;
		HttpContent httpContent = new HttpContent(url, method);
		VolleyerConfiguration nullConfiguration = null;
		Class<String> clazz = String.class;
		// When & Then
		new ResponseBuilder<String>(requestQueue, nullConfiguration, httpContent, clazz);
	}

	@Test(expected=NullPointerException.class)
	public void responseBuilderConstructorShouldThrowNpeWhenHttpContentIsNull() {
		// Given
		HttpContent nullHttpContent = null;
		VolleyerConfiguration configuration = DefaultVolleyerConfigurationFactory.create();
		Class<String> clazz = String.class;
		// When & Then
		new ResponseBuilder<String>(requestQueue, configuration, nullHttpContent, clazz);
	}
	
	@Test(expected=NullPointerException.class)
	public void responseBuilderConstructorShouldThrowNpeWhenTargetClasstIsNull() {
		// Given
		String url = "http://test";
		HttpMethod method = HttpMethod.GET;
		HttpContent httpContent = new HttpContent(url, method);
		VolleyerConfiguration configuration = DefaultVolleyerConfigurationFactory.create();
		Class<String> nullClazz = null;
		// When & Then
		new ResponseBuilder<String>(requestQueue, configuration, httpContent, nullClazz);
	}

	@Test
	public void withListenerMethodShouldReturnSameInstanceOfBuilder() {
		// Given
		String url = "http://test";
		HttpMethod method = HttpMethod.GET;
		HttpContent httpContent = new HttpContent(url, method);
		VolleyerConfiguration configuration = DefaultVolleyerConfigurationFactory.create();
		Class<String> clazz = String.class;
		ResponseBuilder<String> builder = new ResponseBuilder<String>(requestQueue, configuration, httpContent, clazz);
		Listener<String> listener = new Listener<String>(){
			@Override
			public void onResponse(String response) {
			}};
		// When
		ResponseBuilder<String> newBuilder = builder.withListener(listener);
		// Then
		assertTrue(builder == newBuilder);
	}

	@Test
	public void withErrorListenerMethodShouldReturnSameInstanceOfBuilder() {
		// Given
		String url = "http://test";
		HttpMethod method = HttpMethod.GET;
		HttpContent httpContent = new HttpContent(url, method);
		VolleyerConfiguration configuration = DefaultVolleyerConfigurationFactory.create();
		Class<String> clazz = String.class;
		ResponseBuilder<String> builder = new ResponseBuilder<String>(requestQueue, configuration, httpContent, clazz);
		ErrorListener errorListener = new ErrorListener (){
			@Override
			public void onErrorResponse(VolleyError error) {
			}};
		// When
		ResponseBuilder<String> newBuilder = builder.withErrorListener(errorListener);
		// Then
		assertTrue(builder == newBuilder);
	}

	@Test(expected = IllegalStateException.class)
	public void withListenerMethodShouldThrowIllegalStateExceptionWhenExecuteMethodIsAlreadyCalled() {
		// Given
		String url = "http://test";
		HttpMethod method = HttpMethod.GET;
		HttpContent httpContent = new HttpContent(url, method);
		VolleyerConfiguration configuration = DefaultVolleyerConfigurationFactory.create();
		Class<String> clazz = String.class;
		ResponseBuilder<String> builder = new ResponseBuilder<String>(requestQueue, configuration, httpContent, clazz);
		Listener<String> listener = new Listener<String>(){
			@Override
			public void onResponse(String response) {
			}};
		// When
		builder.execute();
		// Then
		builder.withListener(listener);
	}

	@Test(expected = IllegalStateException.class)
	public void withErrorListenerMethodShouldThrowIllegalStateExceptionWhenExecuteMethodIsAlreadyCalled() {
		// Given
		String url = "http://test";
		HttpMethod method = HttpMethod.GET;
		HttpContent httpContent = new HttpContent(url, method);
		VolleyerConfiguration configuration = DefaultVolleyerConfigurationFactory.create();
		Class<String> clazz = String.class;
		ResponseBuilder<String> builder = new ResponseBuilder<String>(requestQueue, configuration, httpContent, clazz);
		ErrorListener errorListener = new ErrorListener (){
			@Override
			public void onErrorResponse(VolleyError error) {
			}};
		// When
		builder.execute();
		// Then
		builder.withErrorListener(errorListener);
	}

}