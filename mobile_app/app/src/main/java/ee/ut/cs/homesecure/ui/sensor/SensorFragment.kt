package ee.ut.cs.homesecure.ui.sensor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ee.ut.cs.homesecure.databinding.FragmentSensorBinding

class SensorFragment : Fragment() {

    private lateinit var sensorViewModel: SensorModel
    private var _binding: FragmentSensorBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SensorModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        sensorViewModel =
            ViewModelProvider(this)[SensorModel::class.java]

        _binding = FragmentSensorBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val root: View = binding.root

        val textViewMotion: TextView = binding.textSensorMotionLabel
        val textViewKnock: TextView = binding.textSensorKnockLabel
        sensorViewModel.textMotion.observe(viewLifecycleOwner, {
            textViewMotion.text = it
        })
        sensorViewModel.textKnock.observe(viewLifecycleOwner, {
            textViewKnock.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
