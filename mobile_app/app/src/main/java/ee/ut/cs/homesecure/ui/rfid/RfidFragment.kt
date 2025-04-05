package ee.ut.cs.homesecure.ui.rfid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ee.ut.cs.homesecure.databinding.FragmentRfidBinding

class RfidFragment : Fragment() {

    private var _binding: FragmentRfidBinding? = null
    private val binding get() = _binding!!
    private lateinit var rfidViewModel: RfidViewModel
    private val viewModel: RfidViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        rfidViewModel = ViewModelProvider(this)[RfidViewModel::class.java]
        _binding = FragmentRfidBinding.inflate(inflater, container, false)

        _binding!!.lifecycleOwner = this
        _binding!!.viewModelRfid = viewModel

        val textView: TextView = binding.textSensorRfidLabel
        rfidViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*   companion object {

       }*/
}
