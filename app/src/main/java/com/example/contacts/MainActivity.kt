package com.example.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var list: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = findViewById(R.id.listView)

        showContactsWrapper()
    }

    private fun showContactsWrapper() {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            checkSelfPermission(Manifest.permission.READ_CONTACTS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSION_REQUEST_READ_CONTACTS
            )
        } else {
            val cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null
            )

            val adapter = SimpleCursorAdapter(
                this, android.R.layout.simple_list_item_2,
                cursor,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                ),

                intArrayOf(
                    android.R.id.text1,
                    android.R.id.text2
                ),
                0
            )

            list.adapter = adapter
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showContactsWrapper()
            } else {
                Toast.makeText(this, "Извините, нет доступ к контактам", Toast.LENGTH_SHORT).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        const val PERMISSION_REQUEST_READ_CONTACTS = 222
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

















//    private fun showContactsWrapper() {
//
//        // Проверим версию SKD
//        // а также есть ли уже нужные разрешения
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
//            checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Запрашиваем нужные разрешения, если их нет
//            requestPermissions(
//                arrayOf<String>(Manifest.permission.READ_CONTACTS),
//                PERMISSIONS_REQUEST_READ_CONTACTS
//            )
//            // Ждем выполнения колбэка onRequestPermissionsResult(int, String[], int[])
//        } else {
//            // Или версия Android меньше 6
//            // или нужные разрешения уже имеются
//            // поэтому запрашиваем данные
//            showContacts()
//        }
//    }
}