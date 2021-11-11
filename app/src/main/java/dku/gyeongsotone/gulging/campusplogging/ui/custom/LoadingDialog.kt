package dku.gyeongsotone.gulging.campusplogging.ui.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import dku.gyeongsotone.gulging.campusplogging.databinding.DialogLoadingBinding
import kotlinx.coroutines.Job

class LoadingDialog(context: Context, message: String? = null) : Dialog(context) {
    private var binding: DialogLoadingBinding = DialogLoadingBinding.inflate(layoutInflater)

    init {
        if (message != null) {
            binding.tvMessage.text = message
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)
    }

    fun setMessage(message: String) {
        binding.tvMessage.text = message
    }

    companion object {

        /**
         * Job 시작 전에 다이얼로그를 띄우고,
         * Job 이 끝나면 다이얼로그 dismiss
         */
        suspend fun showWhileDoJob(context: Context, job: Job, message: String? = null) {
            val dialog = LoadingDialog(context, message)
            dialog.show()
            job.join()
            dialog.dismiss()
        }
    }

}