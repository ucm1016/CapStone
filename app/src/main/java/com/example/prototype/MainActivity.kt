package com.example.prototype

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.prototype.Auth.LoginActivity
import com.example.prototype.Auth.MyInfoActivity
import com.example.prototype.Write.WriteActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private var DB_year = 0
    private var DB_month = 0
    private var DB_day = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cal = java.util.Calendar.getInstance()
        val DB_year = cal.get(java.util.Calendar.YEAR)
        val DB_month = cal.get(java.util.Calendar.MONTH)
        val DB_day = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)

        //write에 uid중복되지않도록 시간넣어줌
        auth = FirebaseAuth.getInstance()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss")
        val formatted = current.format(formatter)





        alarmbt.setOnClickListener {
            if (auth.currentUser == null) {
                //로그인이 안되어잇다면
                Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_LONG).show()

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)

            } else {
                //되어있으면
                val intent = Intent(this, MyInfoActivity::class.java)
                startActivity(intent)

            }
        }


        write.setOnClickListener {

            if (auth.currentUser != null) {
                var Cal_List = DB_Calendar(DB_year.toString(), DB_month.toString(), DB_day.toString() , formatted.toString())

                val intent = Intent(this, WriteActivity::class.java)
                intent.putExtra("Cal_List", Cal_List)
                startActivity(intent)
            } else {
                Toast.makeText(this, "로그인하고 이용하세요.", Toast.LENGTH_LONG).show()
            }
        }



    }

    //메뉴
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.row_menu , menu)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.row ->
                try{ //intent는 Boolean리턴이 아니기 때문에 예외처리를 해준다
                    val intent = Intent(this , rowActivity::class.java)
                    startActivity(intent)
                }catch (e: NumberFormatException){ //숫자 형식이 아닐때
                    return false //아무것도 반환X
                }


        }

        return super.onOptionsItemSelected(item)
    }
}
