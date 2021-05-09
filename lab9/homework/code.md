# 界面部分

##  activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:layout_margin="16dp">

    <TextView
        android:id="@+id/instructions"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Enter URL:"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintHorizontal_bias="0.022"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.007" />

    <EditText
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:id="@+id/urlField"
        app:layout_constraintTop_toBottomOf="@id/instructions"
        app:layout_constraintStart_toEndOf="@+id/mySpinner"
        app:layout_constraintEnd_toEndOf="parent"
        android:hint="www.example.com"
        />
    <androidx.appcompat.widget.AppCompatSpinner
        android:layout_marginTop="8dp"
        android:id="@+id/mySpinner"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/instructions"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        />

    <Button
        android:id="@+id/getSource"
        android:layout_width="171dp"
        android:layout_height="39dp"
        android:layout_marginStart="16dp"
        android:layout_marginLeft="16dp"
        android:layout_marginTop="12dp"
        android:background="@android:color/holo_blue_light"
        android:onClick="getSource"
        android:text="get Page Source"
        android:textColor="@android:color/white"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/urlField" />

    <ScrollView
        android:layout_marginTop="16dp"
        app:layout_constraintTop_toBottomOf="@id/getSource"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        android:layout_marginStart="16dp"
        android:layout_marginEnd="16dp"
        android:id="@+id/scrollView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:outlineAmbientShadowColor="@android:color/holo_blue_light"

        >
        <TextView
            android:id="@+id/soruceInfo"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Page source will appear here"

            />
    </ScrollView>

</androidx.constraintlayout.widget.ConstraintLayout>
```

#  逻辑部分

## NetworkUtils.java(部分)

```java
//getSoruceCode作为类的静态方法，负责请求queryString对应的网页，获得网页返回的内容
static String getSoruceCode(Context context, String queryString, String protocol){
    HttpURLConnection httpURLConnection= null;
    BufferedReader reader=null;
    String htmlSourceCode= null;
    String [] protocolList= context.getResources().getStringArray(R.array.spinner_item);
    try {
        Uri builderUri;
        if(protocol.equals(protocolList[0])) {
            builderUri = Uri.parse(queryString).buildUpon().scheme(HTTP).build();
        }
        else{
            builderUri= Uri.parse(queryString).buildUpon().scheme(HTTPS).build();
        }
        URL requestURL= new URL(builderUri.toString());
        httpURLConnection= (HttpURLConnection) requestURL.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        InputStream inputStream= httpURLConnection.getInputStream();  //获取返回的数据
        reader= new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder= new StringBuilder();
        String line;
        while((line=reader.readLine())!=null){
            stringBuilder.append(line);
            stringBuilder.append("\n");
        }

        if(stringBuilder.length()==0){
            return null;
        }
        htmlSourceCode= stringBuilder.toString();
        return htmlSourceCode;
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        {
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
            if(reader!=null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    if(htmlSourceCode!=null) {
        Log.d(LOG_TAG, htmlSourceCode);
    }
    else{
        Log.d(LOG_TAG, "Null");

    }
    return htmlSourceCode;
}
```



## LoaderPage.java(部分)

```java
//使用AsyncTaskLoader
public class LoaderPage extends AsyncTaskLoader<String> {
    String queryString;
    Context mContext;
    String mTransProtocol;

    public LoaderPage(@NonNull Context context, String queryString, String mTransProtocol) {
        super(context);
        this.mContext=context;
        this.queryString= queryString;
        this.mTransProtocol=mTransProtocol;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getSoruceCode(mContext,queryString,mTransProtocol);

    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
```



##  MainActivity.java(部分)

```java
//将请求的信息
public void getSource(View view) {
    String queryString= mEditText.getText().toString();
    InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    if (inputMethodManager!=null){
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
    ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo=null;
    if(connectivityManager!=null){
        networkInfo=connectivityManager.getActiveNetworkInfo();
    }
    if(networkInfo!=null && networkInfo.isConnected() && queryString.length()!=0) {
        Bundle queryBundle = new Bundle();
        queryBundle.putString("queryString", queryString);
        queryBundle.putString("Protocol", spinenrValue);
        getSupportLoaderManager().restartLoader(0, queryBundle, this);
        mTextView.setText("Loading...");
    }else {
        if(queryString.length()==0){
            mTextView.setText("No website to search");
        }
        else{
            mTextView.setText("No connection");
        }
    }
}

//使用loader的方式请求网页内容封装到bundle中
public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
    String queryString= "";
    String transferProtocol="";
    if(args!=null){
        queryString=args.getString("queryString");
        transferProtocol=args.getString("Protocol");
    }

    return new LoaderPage(this,queryString,transferProtocol); //请求网页内容
}

//数据加载完成后页面显示返回的内容
public void onLoadFinished(@NonNull Loader<String> loader, String data) {
    try{
        mTextView.setText(data);
    } catch (Exception e) {
        e.printStackTrace();
        mTextView.setText("No Response");
    }
}

```



