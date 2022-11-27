package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var dotExist = false
    var lastInput = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.output.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                binding.output.text = binding.output.text.removeSuffix(binding.output.text.last().toString())
                if(binding.output.length()==0){
                    lastInput = false
                    dotExist = false
                }
                else if(!isOperatorSelected(binding.output.text.toString())){
                    lastInput = true
                    dotExist = binding.output.text.contains(".")
                }
                else if(isOperatorSelected(binding.output.text.toString())){
                    val splitedArray = binding.output.text.split(doesContain())
                    val secondSide = splitedArray[1]
                    dotExist = secondSide.contains(".")
                    lastInput = secondSide != ""
                }
            }
        })
    }

    fun doesContain():String{
        val text = binding.output.text.toString()
        if(text.contains("+")){
            return "+"
        }
        else if(text.contains("*")){
            return "*"
        }
        else if(text.contains("/")){
            return "/"
        }
        else if(text.contains("@")){
            return "@"
        }
        else if(text.contains("%")){
            return "%"
        }
        if(text.startsWith("-")){
            if(text.substring(1).contains("-")){
                return "-"
            }
        }
        return ""
    }

    fun OnDigitClick(view: View){
        val buttunCLicked = view as Button
        binding.output.append(buttunCLicked.text)
        lastInput = true
    }

    fun onClearText(view: View){
        binding.output.text = ""
        dotExist = false
        lastInput = false
    }

    fun onDecimalPointClicked(view: View){
        if(lastInput && !dotExist){
            binding.output.append(".")
            dotExist = true
            lastInput = false
        }
        else if(!lastInput && !dotExist){
            binding.output.append("0.")
            dotExist = true
            lastInput = false
        }
    }

    fun isOperatorSelected(text: String):Boolean{
        if(text.startsWith("-")){
            return text.substring(1).contains("+") || text.substring(1).contains("-") || text.substring(1).contains("*") || text.substring(1).contains("/") || text.substring(1).contains("@") || text.substring(1).contains("%")
        }else{
            return text.contains("+") || text.contains("-") || text.contains("*") || text.contains("/") || text.contains("@") || text.contains("%")
        }
    }

    fun onOperatorClicked(view: View){
        val button = view as Button
        if(lastInput && !isOperatorSelected(binding.output.text.toString())){
            binding.output.append(button.text)
            lastInput = false
            dotExist = false
        }
        else if(lastInput && isOperatorSelected(binding.output.text.toString())){
            onEqualClicked(view)
            binding.output.append(button.text)
            lastInput = false
            dotExist = false
        }
        else if(!lastInput && isOperatorSelected(binding.output.text.toString()) && binding.output.text.substring(binding.output.text.length-2) != "0.") {
            binding.output.text = binding.output.text.removeSuffix(binding.output.text.last().toString())
            binding.output.append(button.text)
            lastInput = false
            dotExist = false
        }
    }

    fun onEqualClicked(view: View){
        var prefix = ""
        if(!lastInput) return
        var inputValue = binding.output.text.toString()

            if(inputValue.contains("+")){
                val splitedArray = inputValue.split("+")
                val firstNumber = splitedArray[0].toDouble()
                val secondNumber = splitedArray[1].toDouble()
                val result = firstNumber + secondNumber
                if (result.toString().contains(".0") && result.toString().last().toString()=="0"){
                    val intResult = result.toString().split(".0")[0]
                    binding.output.text = intResult
                }else{
                    binding.output.text = result.toString()
                }
            }
            else if(inputValue.contains("*")){
                val splitedArray = inputValue.split("*")
                var firstNumber = splitedArray[0]
                val secondNumber = splitedArray[1]
                val result = firstNumber.toDouble() * secondNumber.toDouble()
                if (result.toString().contains(".0") && result.toString().last().toString()=="0"){
                    val intResult = result.toString().split(".0")[0]
                    binding.output.text = intResult
                }else{
                    binding.output.text = result.toString()
                }            }
            else if(inputValue.contains("/")){
                val splitedArray = inputValue.split("/")
                var firstNumber = splitedArray[0]
                val secondNumber = splitedArray[1]
                val result = firstNumber.toDouble() / secondNumber.toDouble()
                if (result.toString().contains(".0") && result.toString().last().toString()=="0"){
                    val intResult = result.toString().split(".0")[0]
                    binding.output.text = intResult
                }else{
                    binding.output.text = result.toString()
                }            }
            else if(inputValue.contains("%")){
                val splitedArray = inputValue.split("%")
                var firstNumber = splitedArray[0]
                val secondNumber = splitedArray[1]
                val result = firstNumber.toDouble() % secondNumber.toDouble()
                if (result.toString().contains(".0") && result.toString().last().toString()=="0"){
                    val intResult = result.toString().split(".0")[0]
                    binding.output.text = intResult
                }else{
                    binding.output.text = result.toString()
                }            }
            else if(inputValue.contains("@")){
                val splitedArray = inputValue.split("@")
                var firstNumber = splitedArray[0]
                val secondNumber = splitedArray[1]
                val result = (firstNumber.toDouble() * secondNumber.toDouble()) / 100.0
                if (result.toString().contains(".0") && result.toString().last().toString()=="0"){
                    val intResult = result.toString().split(".0")[0]
                    binding.output.text = intResult
                }else{
                    binding.output.text = result.toString()
                }
            }
        if(inputValue.startsWith("-")){
            prefix = "-"
            inputValue = inputValue.substring(1)
        }
            if(inputValue.contains("-")){
                val splitedArray = inputValue.split("-")
                var firstNumber = splitedArray[0]
                val secondNumber = splitedArray[1]
                if(!prefix.isEmpty()){
                    firstNumber = prefix + firstNumber
                }
                val result = firstNumber.toDouble() - secondNumber.toDouble()
                if (result.toString().contains(".0") && result.toString().last().toString()=="0"){
                    val intResult = result.toString().split(".0")[0]
                    binding.output.text = intResult
                }else{
                    binding.output.text = result.toString()
                }
            }
    }

}