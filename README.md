# Sample share files

This sample application demonstrates how to implement the application to share files to other application.

## Instruction
This instruction is showing how to setup file sharing.

### 1. Specify sharable directories
Create `[Project]/app/src/main/res/xml/filepaths.xml`.
```
<paths>
    <files-path path="images/" name="myimages" />
</paths>
```

| Attributes | Meaning |
| ---------- | ------- |
| path | Defining a share directory in a `files` of your application's internal storage. |
| name | Adding the path segment `myimages` to a content URI for files. |

```
content://com.kvsoftware.sharefile.fileprovider/myimages/xxx.jpg
```


### 2. Specify the FileProvider
Put `<provider>` as below in `AndroidManifest.xml`.

````
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.kvsoftware.sharefile">

    <application
    	...
        <provider
            android:name="androidx.core.content.FileProvider"
            android:authorities="com.kvsoftware.sharefile.fileprovider"
            android:grantUriPermissions="true"
            android:exported="false">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/filepaths" />
        </provider>

    </application>

</manifest>
````

### 3. Share files
Create `Uri` with the method `getUriForFile()` of `FileProvider` component and send data with an `Intent`.

```
val file = /* your file */

// Generate a file's content URI.
val uri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", it)

// Send an Intent that contains the data.
val intent = Intent(Intent.ACTION_SEND)
intent.type = "image/jpeg"
intent.putExtra(Intent.EXTRA_STREAM, uri)
startActivity(intent)
```