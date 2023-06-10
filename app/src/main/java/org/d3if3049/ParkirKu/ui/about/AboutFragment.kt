package org.d3if3049.ParkirKu.ui.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.d3if3049.ParkirKu.R
import org.d3if3049.ParkirKu.databinding.FragmentAboutBinding

class AboutFragment : Fragment(){
private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
binding = FragmentAboutBinding.inflate(layoutInflater, container, false)
return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageViewInstagram.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/fadhlansyauqi/")
                )
            )
        }

        binding.imageViewGithub.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/fadhlansyauqi")
                )
            )
        }
        binding.imageViewLinkedIn.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/in/fadhlansyauqi")
                )
            )
        }

binding.button2.setOnClickListener{
it.findNavController().navigate(R.id.action_fragmentMain_to_aboutFragment)
}

binding.button2.setOnClickListener {
val mailTo ="mailto: mailto: adhansyauqi@gmail.com" +
        "?subject=" + Uri.encode("Saran/Kritik App ParkirKu")

    startActivity(
Intent(Intent.ACTION_SENDTO).apply {
data =Uri.parse(mailTo)
}
    )
}
    }
}