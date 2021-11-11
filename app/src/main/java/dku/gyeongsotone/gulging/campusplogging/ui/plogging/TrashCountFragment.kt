package dku.gyeongsotone.gulging.campusplogging.ui.plogging

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dku.gyeongsotone.gulging.campusplogging.BR
import dku.gyeongsotone.gulging.campusplogging.R
import dku.gyeongsotone.gulging.campusplogging.databinding.FragmentTrashCountBinding
import dku.gyeongsotone.gulging.campusplogging.ui.custom.TrashCountView
import dku.gyeongsotone.gulging.campusplogging.utils.Constant
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.TRASH_COUNT_MAX
import dku.gyeongsotone.gulging.campusplogging.utils.Constant.TRASH_COUNT_MIN
import dku.gyeongsotone.gulging.campusplogging.utils.compress
import java.io.File


class TrashCountFragment : Fragment() {
    companion object {
        private val TAG = this::class.java.name
    }

    private lateinit var imageUri: Uri
    private lateinit var imageFile: File

    private lateinit var binding: FragmentTrashCountBinding
    private val viewModel: PloggingViewModel by activityViewModels()

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                val picture = getBitmapFromUri()
                viewModel.picture = picture.compress()
                navigateToPloggingFinishFragment()
            } else {
                showToast("사진을 불러오지 못했습니다. 다시 시도해주세요.")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        init(inflater, container)
        setOnClickListener()
        addTrashCountViews()
        (requireActivity() as PloggingActivity).setBackPressable(true)

        return binding.root
    }

    /** binding 설정 */
    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_trash_count,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setSpannableText()
    }

    /** spannable text 설정 */
    private fun setSpannableText() {
        val titleTextViewSpannable = SpannableStringBuilder("모은 쓰레기를 입력하세요")
        titleTextViewSpannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.main_yellow)),
            0,
            6,
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )
        binding.tvTitle.text = titleTextViewSpannable
    }


    /** 클릭 리스너 설정 */
    private fun setOnClickListener() {
        binding.btnNext.setOnClickListener { onNextBtnClick() }
    }

    /** 사진 촬영 후 plogging finish 프래그먼트로 이동 */
    private fun onNextBtnClick() {
        imageFile = File.createTempFile("picture_", ".jpeg")
        imageUri = FileProvider.getUriForFile(
            requireContext(),
            "fileprovider",
            imageFile
        )

        takePicture.launch(imageUri)
    }

    private fun navigateToPloggingFinishFragment() {
        findNavController().navigate(
            TrashCountFragmentDirections.actionTrashInputFragmentToPloggingFinishFragment()
        )
    }

    /** grid layout에 trash count views 추가*/
    private fun addTrashCountViews() {
        for (trashKind in Constant.TRASH_TYPES_KOR) {
            val newView = TrashCountView(requireContext())
            val countsInViewModel = when (trashKind) {
                "플라스틱" -> viewModel.plastics
                "비닐" -> viewModel.vinyls
                "유리" -> viewModel.glasses
                "캔" -> viewModel.cans
                "종이" -> viewModel.papers
                "일반쓰레기" -> viewModel.generals
                else -> null
            }

            // set text and click listener
            newView.binding.tvTrashKind.text = trashKind
            newView.binding.ivMinus.setOnClickListener {
                val count = countsInViewModel?.get()
                if (count != null && count > TRASH_COUNT_MIN) {
                    countsInViewModel.set(count - 1)
                }
            }
            newView.binding.ivPlus.setOnClickListener {
                val count = countsInViewModel?.get()
                if (count != null && count < TRASH_COUNT_MAX) {
                    countsInViewModel.set(count + 1)
                }
            }

            // set data binding variable
            newView.binding.setVariable(BR.trashCount, countsInViewModel)

            // add view on grid layout
            binding.gridLayout.addView(newView)

            // set grid layout params
            (newView.layoutParams as GridLayout.LayoutParams).apply {
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
        }
    }


    private fun getBitmapFromUri(): Bitmap {
        val exif = ExifInterface(imageFile.path)
        val orientation = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )
        var bitmap =
            BitmapFactory.decodeFile(imageFile.path)

        // 이미지 압축
        val sizeMB = bitmap.byteCount.toDouble() / (1024 * 1024)
        if (sizeMB > 10) {
            val ratio = 10 / sizeMB
            bitmap = Bitmap.createScaledBitmap(
                bitmap,
                (bitmap.width * ratio).toInt(),
                (bitmap.height * ratio).toInt(),
                true
            )
        }

        // 정방향으로 회전
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_NORMAL -> matrix.setRotate(0f)
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(270f)
        }

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}