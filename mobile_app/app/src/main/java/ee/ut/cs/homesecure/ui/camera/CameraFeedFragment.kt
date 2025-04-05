package ee.ut.cs.homesecure.ui.camera

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ee.ut.cs.homesecure.databinding.FragmentCameraFeedBinding
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout
/*
* @see https://code.videolan.org/videolan/libvlc-android-samples
*/

class CameraFeedFragment : Fragment() {


    private val url = "http://www.kuutsemae.ee/lilleoru-live.html"

    private var libVlc: LibVLC? = null
    private var mediaPlayer: MediaPlayer? = null
    private var vlcVideoLayout: VLCVideoLayout? = null
    private var _binding: FragmentCameraFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var cameraViewModel: CameraFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        cameraViewModel = ViewModelProvider(this)[CameraFeedViewModel::class.java]
        _binding = FragmentCameraFeedBinding.inflate(inflater, container, false)

        libVlc = LibVLC(context)
        mediaPlayer = MediaPlayer(libVlc)
        vlcVideoLayout = _binding!!.videoLayout
        cameraViewModel.status.value?.let { Log.i("-------Camera address", it) }

        return binding.root

    }

    override fun onStart() {
        super.onStart()
        vlcVideoLayout?.let {
            mediaPlayer!!.attachViews(it,
                null,
                ENABLE_SUBTITLES,
                USE_TEXTURE_VIEW)
        }

        val media = Media(libVlc, Uri.parse(url))
        media.setHWDecoderEnabled(true, false)
        media.addOption(":network-caching=800")

        mediaPlayer!!.media = media
        media.release()
        mediaPlayer!!.play()
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.stop()
        mediaPlayer?.detachViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer?.release()
        libVlc?.release()
        _binding = null

    }

    companion object {
        private const val USE_TEXTURE_VIEW = false
        private const val ENABLE_SUBTITLES = true

    }


}
