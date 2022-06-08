package com.example.teampel_1.ui.workout

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teampel_1.R
import com.example.teampel_1.databinding.FragmentWorkoutBinding
import kotlinx.android.synthetic.main.custom_dialog.view.*
import kotlinx.android.synthetic.main.fragment_workout.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.os.CountDownTimer

class DashboardFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    val data:ArrayList<WorkoutData> = ArrayList()
    lateinit var adapter:WorkoutAdapter
    private var _binding: FragmentWorkoutBinding? = null
    var counter:Int = 0
    var isStopwatch :Boolean = true
    var emojiID:Int = 0
    var userAssessment:String = ""
    lateinit var dlgView:View
    lateinit var dlgWindow:AlertDialog
    lateinit var timer: CountDownTimer
    lateinit var timerStart: Button
    lateinit var timerStop : Button
    lateinit var timerPause : Button
    lateinit var timerResume : Button
    lateinit var countTime: TextView
    var milileft: Long = 0
    var flagTimer: Boolean = true

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        val root: View = binding.root
        init()
        initRecyclerView()
        initTimer()
        return root
    }

    private fun initTimer() {
        flagTimer = true
        timer = object : CountDownTimer(0,0){
            override fun onTick(p0: Long) {
            }
            override fun onFinish() {
            }
        }

        timerStart = binding.root.findViewById(R.id.startBtn)
        timerStop = binding.root.findViewById(R.id.stopBtn)
        timerPause = binding.root.findViewById(R.id.pauseBtn)
        timerResume = binding.root.findViewById(R.id.resumeBtn)
        countTime = binding.root.findViewById(R.id.timerDisplay)

        //counter = getWorkoutTimerChallenge()

        timerStart.setOnClickListener {
            if (isStopwatch) startStopwatch(10000)
            else startTimer(10000)
        }

        timerStop.setOnClickListener { stopTimer() }
        timerPause.setOnClickListener { pauseTimer() }
        timerResume.setOnClickListener { resumeTimer() }
    }

    private fun startTimer(milli: Long){
        //getString()
//        countTime.text = "00:00:10"
//        counter = 10
        if(milli!=milileft) counter = 10
        flagTimer = true

        timer = object : CountDownTimer(milli, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                milileft = millisUntilFinished
                countTime.text = String.format(
                        "%02d:%02d:%02d", counter / 3600,
                        (counter % 3600) / 60, (counter % 60)
                )
                counter--
            }

            override fun onFinish() {
                flagTimer = false
                countTime.text = "00:00:00"
                playAudio()
                showDialog()
                Toast.makeText(binding.root.context, "TIME IS UP!", Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    private fun startStopwatch(milli: Long){
        //getString()
        //counter = getWorkoutTimerChallenge()
        if(milli!=milileft) counter = 0
        flagTimer = true

        timer = object : CountDownTimer(milli, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                milileft = millisUntilFinished
                countTime.text = String.format(
                        "%02d:%02d:%02d", counter / 3600,
                        (counter % 3600) / 60, (counter % 60)
                )
                counter++
            }

            override fun onFinish() {
                flagTimer = false
                countTime.text = "00:00:00"
                playAudio()
                showDialog()
//                Toast.makeText(binding.root.context, "TIME IS UP!", Toast.LENGTH_LONG).show()
            }
        }.start()
    }

    private fun pauseTimer(){
        timer.cancel()
        flagTimer = true
    }

    private fun resumeTimer(){
        if(flagTimer && isStopwatch) startStopwatch(milileft)
        else if(flagTimer && !isStopwatch) startTimer(milileft)
}

    private  fun stopTimer(){
        timer.cancel()
        timer.onFinish()
        //println(countTime.text)
    }

    private fun playAudio(){
        val BgMusic = MediaPlayer.create(binding.root.context, R.raw.noti)
        BgMusic?.start()
    }

    private fun initRecyclerView() {
        recyclerView = binding.root.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context,
            LinearLayoutManager.VERTICAL, false)
        adapter = WorkoutAdapter(data)

//        adapter.itemClickListener = object : WorkoutAdapter.OnItemClickListener {
//            override fun OnItemClickListener(data: WorkoutData) {
//
//                if(isReady){
//                    (text, mode, parameter, ID)
//                    tts.speak(data.word, TextToSpeech.QUEUE_FLUSH, null, null)
//                }

//                Toast.makeText(applicationContext, data.meaning, Toast.LENGTH_SHORT).show()
//            }

//        }
//        recyclerView.adapter = adapter

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or
                ItemTouchHelper.DOWN, ItemTouchHelper.LEFT){
            override fun onMove(
                p0: RecyclerView,
                p1: RecyclerView.ViewHolder,
                p2: RecyclerView.ViewHolder
            ): Boolean {
                adapter.moveItem(p1.adapterPosition, p2.adapterPosition)
                return false
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
//        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun init() {
//        val scan = Scanner(resources.openRawResource(R.raw.words))
//        while (scan.hasNextLine()){
//            val word = scan.nextLine()
//            val meaning = scan.nextLine()

//            WorkoutData.add(WorkoutData(word, meaning))
//        }

//        val date = getCurrentDateTime()
//        val dateInString = date.toString("yyyy-MM-dd HH:mm:ss")
//        val textView = binding.root.findViewById<TextView>(R.id.timerDisplay)

//        ===================== initialization of dialog =========================
        dlgView = LayoutInflater.from(binding.root.context).inflate(R.layout.custom_dialog, null, false)
        dlgWindow = AlertDialog.Builder(binding.root.context)
            .setView(dlgView)
            .setCancelable(false)
            .setPositiveButton("확인"){
                    dialog, _ -> dialog.dismiss()
            }.create()
        binding.dlgView.setOnClickListener {
//          show dialog when TextView "VIEW DIALOG" is clicked
            showDialog()
        }

        val completeWorkoutBtn = binding.root.completeWorkoutBtn
        completeWorkoutBtn.setOnClickListener {
            Toast.makeText(binding.root.context,"일기탭에 이동",Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialog(){
        dlgWindow.setOnShowListener {
            val btn = dlgWindow.getButton(AlertDialog.BUTTON_POSITIVE)
            btn.setTextColor(getColor(binding.root.context, R.color.colorCustom))
            btn.gravity = Gravity.CENTER
            btn.textSize = 16F
            btn.setBackgroundColor(getColor(binding.root.context, R.color.colorCustom2))

            btn.setOnClickListener {
                if(emojiID == 0){
                    Toast.makeText(binding.root.context,"이모지를 선택하세요",Toast.LENGTH_LONG).show()
                } else {
                    userAssessment = dlgView.comment.text.toString()
                    dlgView.comment.text?.clear()
                    dlgWindow.dismiss()
                    println(emojiID)
                    println(userAssessment)
                }
            }
        }

//      Set custom title for dialog
        val title = TextView(binding.root.context)
        title.text = "운동은 어떠셨나요?"
        title.setBackgroundColor(getColor(binding.root.context, R.color.colorCustom2))
        title.setPadding(0, 30, 0, 30)
        title.gravity = Gravity.CENTER
        title.setTextColor(getColor(binding.root.context, R.color.colorCustom))
        title.textSize = 20F

        val radioGroup = dlgView.findViewById<RadioGroup>(R.id.radioGroup)
        val btn1 = dlgView.findViewById<RadioButton>(R.id.emoji1btn)
        val btn2 = dlgView.findViewById<RadioButton>(R.id.emoji2btn)
        val btn3 = dlgView.findViewById<RadioButton>(R.id.emoji3btn)

        dlgWindow.setCustomTitle(title)
        dlgWindow.show()
        dlgWindow.withCenteredButtons()

        btn1.background.setTint(Color.GRAY)
        btn2.background.setTint(Color.GRAY)
        btn3.background.setTint(Color.GRAY)

        emojiID = 0
        userAssessment = ""

        radioGroup.setOnCheckedChangeListener { radioGroup, checkedID ->
            if(checkedID == R.id.emoji1btn){
                emojiID = 1
                btn1.background.setTint(Color.GREEN)
                btn2.background.setTint(Color.GRAY)
                btn3.background.setTint(Color.GRAY)
            }else if(checkedID == R.id.emoji2btn){
                emojiID = 2
                btn2.background.setTint(Color.YELLOW)
                btn1.background.setTint(Color.GRAY)
                btn3.background.setTint(Color.GRAY)
            }else if(checkedID == R.id.emoji3btn){
                emojiID = 3
                btn3.background.setTint(Color.RED)
                btn1.background.setTint(Color.GRAY)
                btn2.background.setTint(Color.GRAY)
            }
        }
    }

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        timer?.cancel()
    }

    fun AlertDialog.withCenteredButtons() {
        val positive = getButton(AlertDialog.BUTTON_POSITIVE)
        val negative = getButton(AlertDialog.BUTTON_NEGATIVE)

        //Disable the material spacer view in case there is one
        val parent = positive.parent as? LinearLayout
        parent?.gravity = Gravity.CENTER_HORIZONTAL
        val leftSpacer = parent?.getChildAt(1)
        leftSpacer?.visibility = View.GONE

        //Force the default buttons to center
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        layoutParams.weight = 0.1f
        layoutParams.gravity = Gravity.CENTER

        positive.layoutParams = layoutParams
        negative.layoutParams = layoutParams
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }
}