package com.zyyoona7.demo

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import android.widget.Toast
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.zyyoona7.demo.activity.BaseActivity
import com.zyyoona7.demo.databinding.ActivityMain5Binding
import com.zyyoona7.demo.utils.typefaceLight
import com.zyyoona7.demo.utils.typefaceMedium
import com.zyyoona7.demo.utils.typefaceRegular
import com.zyyoona7.demo.utils.vibrateShot
import com.zyyoona7.wheel.WheelView
import com.zyyoona7.wheel.formatter.IntTextFormatter
import com.zyyoona7.wheel.listener.OnItemPositionChangedListener
import java.util.*
import kotlin.random.Random


class Main5Activity : BaseActivity<ActivityMain5Binding>(), SeekBar.OnSeekBarChangeListener {

    override fun initLayoutId(): Int {
        return R.layout.activity_main5
    }

    override fun initVariables(savedInstanceState: Bundle?) {
        initWheelData()
        initDefaultWheelAttrs()
        initOperationsValue()
    }

    override fun initListeners(savedInstanceState: Bundle?) {

        binding.btnTimePicker.setOnClickListener {
            TimePickerActivity.start(this)
        }

        binding.btnDatePicker.setOnClickListener {
            DatePickerActivity.start(this)
        }

        binding.btnLinkagePicker.setOnClickListener {
            LinkagePickerActivity.start(this)
        }

        binding.sbVisibleItems.setOnSeekBarChangeListener(this)
        binding.sbLineSpacing.setOnSeekBarChangeListener(this)
        binding.sbSoundVolume.setOnSeekBarChangeListener(this)
        binding.sbTextSize.setOnSeekBarChangeListener(this)
        binding.sbTextBoundaryMargin.setOnSeekBarChangeListener(this)
        binding.sbCurvedArcFactor.setOnSeekBarChangeListener(this)
        binding.sbRefract.setOnSeekBarChangeListener(this)
        binding.sbDividerHeight.setOnSeekBarChangeListener(this)
        binding.sbDividerPadding.setOnSeekBarChangeListener(this)
        binding.sbDividerOffset.setOnSeekBarChangeListener(this)

        binding.scCyclic.setOnCheckedChangeListener { _, isChecked ->
            binding.wheelview.isCyclic = isChecked
        }

        binding.scSound.setOnCheckedChangeListener { _, isChecked ->
            binding.wheelview.isSoundEffect = isChecked
        }

        binding.scHasCurtain.setOnCheckedChangeListener { _, isChecked ->
            binding.wheelview.isShowCurtain = isChecked
        }

        binding.scCurved.setOnCheckedChangeListener { _, isChecked ->
            binding.wheelview.isCurved = isChecked
        }

        binding.scShowDivider.setOnCheckedChangeListener { _, isChecked ->
            binding.wheelview.isShowDivider = isChecked
        }

        binding.scCanOverRangeScroll.setOnCheckedChangeListener { _, isChecked ->
            binding.wheelview.canOverRangeScroll = isChecked
        }

        binding.rgAlign.setOnCheckedChangeListener { _, checkedId ->
            binding.wheelview.textAlign = when (checkedId) {
                R.id.rb_align_left -> WheelView.TEXT_ALIGN_LEFT
                R.id.rb_align_right -> WheelView.TEXT_ALIGN_RIGHT
                else -> WheelView.TEXT_ALIGN_CENTER
            }
        }

        binding.rgDirection.setOnCheckedChangeListener { _, checkedId ->
            binding.wheelview.curvedArcDirection = when (checkedId) {
                R.id.rb_direction_left -> WheelView.CURVED_ARC_DIRECTION_LEFT
                R.id.rb_direction_right -> WheelView.CURVED_ARC_DIRECTION_RIGHT
                else -> WheelView.CURVED_ARC_DIRECTION_CENTER
            }
        }

        binding.rgDividerType.setOnCheckedChangeListener { _, checkedId ->
            binding.wheelview.dividerType = when (checkedId) {
                R.id.rb_divider_fill -> WheelView.DIVIDER_FILL
                else -> WheelView.DIVIDER_WRAP
            }
        }

        binding.rgDividerCap.setOnCheckedChangeListener { _, checkedId ->
            binding.wheelview.dividerCap = when (checkedId) {
                R.id.rb_divider_cap_square -> Paint.Cap.SQUARE
                R.id.rb_divider_cap_butt -> Paint.Cap.BUTT
                else -> Paint.Cap.ROUND
            }
        }


        binding.btnSelected.setOnClickListener {
            if (binding.cbSelectedRandom.isChecked) {
                binding.wheelview.setSelectedPosition(
                        Random.nextInt(0, binding.wheelview.getAdapter()?.getItemCount() ?: 0),
                        binding.cbSelectedSmooth.isChecked,
                        binding.sbSelectedDuration.progress)
            } else {
                binding.wheelview.setSelectedPosition(binding.brSelectedPosition.currentValue.toInt(),
                        binding.cbSelectedSmooth.isChecked,
                        binding.sbSelectedDuration.progress)
            }
        }

        binding.btnCurtainColor.setOnClickListener {
            showColorPicker(binding.wheelview.curtainColor) {
                binding.wheelview.curtainColor = it
                binding.btnCurtainColor.setBackgroundColor(it)
            }
        }

        binding.btnSelectedColor.setOnClickListener {
            showColorPicker(binding.wheelview.selectedTextColor) {
                binding.wheelview.selectedTextColor = it
                binding.btnSelectedColor.setBackgroundColor(it)
            }
        }

        binding.btnNormalColor.setOnClickListener {
            showColorPicker(binding.wheelview.normalTextColor) {
                binding.wheelview.normalTextColor = it
                binding.btnNormalColor.setBackgroundColor(it)
            }
        }

        binding.btnDividerColor.setOnClickListener {
            showColorPicker(binding.wheelview.dividerColor) {
                binding.wheelview.dividerColor = it
                binding.btnDividerColor.setBackgroundColor(it)
            }
        }

        binding.btnFontMedium.setOnClickListener {
            binding.wheelview.setTypeface(typefaceMedium(), binding.cbBoldSelected.isChecked)
        }

        binding.btnFontRegular.setOnClickListener {
            binding.wheelview.setTypeface(typefaceRegular(), binding.cbBoldSelected.isChecked)
        }

        binding.btnFontLight.setOnClickListener {
            binding.wheelview.setTypeface(typefaceLight(), binding.cbBoldSelected.isChecked)
        }

        binding.rsbSelectedRange.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onRangeChanged(view: RangeSeekBar?, leftValue: Float,
                                        rightValue: Float, isFromUser: Boolean) {
                binding.wheelview.setSelectedRange(leftValue.toInt(), rightValue.toInt())
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

        })

        binding.wheelview.setOnItemPositionChangedListener(object : OnItemPositionChangedListener {
            override fun onItemChanged(wheelView: WheelView, oldPosition: Int, newPosition: Int) {
                if (binding.scVibrate.isChecked) {
                    vibrateShot(10)
                }
            }
        })
    }

    private fun initWheelData() {
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH) - 1
        calendar.set(Calendar.DATE, 1)
        calendar.roll(Calendar.DATE, -1)
        val monthOfDays = calendar.get(Calendar.DATE)
        val daysList = arrayListOf<Int>()
        for (i in 1..monthOfDays) {
            daysList.add(i)
        }
        binding.wheelview.setData(daysList)
        binding.wheelview.setTextFormatter(IntTextFormatter("%d日"))
        binding.wheelview.setSelectedPosition(currentDay)

        Handler().postDelayed({
            Toast.makeText(this, "选中${binding.wheelview.getSelectedItem<Int>() ?: -1}",
                    Toast.LENGTH_LONG).show()
        }, 1000)

    }

    private fun initDefaultWheelAttrs() {
        binding.wheelview.setTextSize(18f)
        binding.wheelview.setLineSpacing(10f)
        binding.wheelview.setSoundResource(R.raw.button_choose)
        binding.wheelview.getAdapter()?.getSelectedItem<Int>()
        binding.wheelview.curtainColor = Color.WHITE
    }

    private fun initOperationsValue() {
        val wheelView = binding.wheelview
        binding.sbVisibleItems.max = 9
        binding.sbVisibleItems.progress = wheelView.visibleItems

        binding.scCyclic.isChecked = wheelView.isCyclic

        binding.sbLineSpacing.max = 50
        binding.sbLineSpacing.progress = wheelView.lineSpacing.toInt()

        binding.sbSelectedDuration.max = 3000
        binding.sbSelectedDuration.progress = WheelView.DEFAULT_SCROLL_DURATION

        binding.scSound.isChecked = wheelView.isSoundEffect
        binding.sbSoundVolume.max = 100
        binding.sbSoundVolume.progress = (wheelView.getSoundVolume() * 100).toInt()

        binding.scHasCurtain.isChecked = wheelView.isShowCurtain
        binding.btnCurtainColor.setBackgroundColor(wheelView.curtainColor)

        binding.sbTextSize.max = 70
        binding.sbTextSize.progress = wheelView.textSize.toInt()

        when (wheelView.textAlign) {
            WheelView.TEXT_ALIGN_LEFT -> binding.rbAlignLeft.isChecked = true
            WheelView.TEXT_ALIGN_RIGHT -> binding.rbAlignRight.isChecked = true
            else -> binding.rbAlignCenter.isChecked = true
        }

        binding.btnSelectedColor.setBackgroundColor(wheelView.selectedTextColor)
        binding.btnNormalColor.setBackgroundColor(wheelView.normalTextColor)

        binding.sbTextBoundaryMargin.max = 150
        binding.sbTextBoundaryMargin.progress = wheelView.textPaddingLeft.toInt()

        binding.cbBoldSelected.isChecked = wheelView.isBoldForSelectedItem()

        binding.scCurved.isChecked = wheelView.isCurved
        when (wheelView.curvedArcDirection) {
            WheelView.CURVED_ARC_DIRECTION_LEFT -> binding.rbDirectionLeft.isChecked = true
            WheelView.CURVED_ARC_DIRECTION_RIGHT -> binding.rbDirectionRight.isChecked = true
            else -> binding.rbDirectionCenter.isChecked = true
        }
        binding.sbCurvedArcFactor.max = 100
        binding.sbCurvedArcFactor.progress = (wheelView.curvedArcDirectionFactor * 100).toInt()
        binding.sbRefract.max = 100
        binding.sbRefract.progress = (wheelView.refractRatio * 100).toInt()

        binding.scShowDivider.isChecked = wheelView.isShowDivider
        binding.sbDividerHeight.max = 30
        binding.sbDividerHeight.progress = wheelView.dividerHeight.toInt()
        binding.btnDividerColor.setBackgroundColor(wheelView.dividerColor)
        when (wheelView.dividerType) {
            WheelView.DIVIDER_FILL -> binding.rbDividerFill.isChecked = true
            else -> binding.rbDividerWrap.isChecked = true
        }
        binding.sbDividerPadding.max = 60
        binding.sbDividerPadding.progress = wheelView.dividerPadding.toInt()
        binding.sbDividerOffset.max = 30
        binding.sbDividerOffset.progress = wheelView.dividerOffsetY.toInt()
        when (binding.wheelview.dividerCap) {
            Paint.Cap.SQUARE -> binding.rbDividerCapSquare.isChecked = true
            Paint.Cap.BUTT -> binding.rbDividerCapButt.isChecked = true
            else -> binding.rbDividerCapRound.isChecked = true
        }

        binding.btnFontMedium.typeface = typefaceMedium()
        binding.btnFontRegular.typeface = typefaceRegular()
        binding.btnFontLight.typeface = typefaceLight()

        val maxValue = wheelView.getAdapter()?.getItemCount()?.minus(1) ?: 0
        binding.rsbSelectedRange.setRange(0f,
                maxValue.toFloat())
        binding.rsbSelectedRange.setProgress(0f, binding.rsbSelectedRange.maxProgress)

        binding.scCanOverRangeScroll.isChecked = wheelView.canOverRangeScroll

        binding.brSelectedPosition.setValue(0f, maxValue.toFloat(), 0f, 1f, 5)

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (seekBar == null) {
            return
        }
        when (seekBar.id) {
            R.id.sb_visible_items -> binding.wheelview.visibleItems = progress
            R.id.sb_line_spacing -> binding.wheelview.lineSpacing = progress
            R.id.sb_sound_volume -> binding.wheelview.setSoundVolume(progress / 100f)
            R.id.sb_text_size -> binding.wheelview.textSize = progress
            R.id.sb_text_boundary_margin -> {
                binding.wheelview.textPaddingLeft = progress
                binding.wheelview.textPaddingRight = progress
            }
            R.id.sb_curved_arc_factor -> binding.wheelview.curvedArcDirectionFactor = progress / 100f
            R.id.sb_refract -> binding.wheelview.refractRatio = progress / 100f
            R.id.sb_divider_height -> binding.wheelview.dividerHeight = progress
            R.id.sb_divider_padding -> binding.wheelview.dividerPadding = progress
            R.id.sb_divider_offset -> binding.wheelview.dividerOffsetY = progress
        }
    }

    private fun showColorPicker(initialColor: Int, block: (Int) -> Unit) {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("选择颜色")
                .initialColor(initialColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("设置") { _, selectedColor, _ ->
                    block.invoke(selectedColor)
                }
                .setNegativeButton("取消", null)
                .build()
                .show()
    }

}
