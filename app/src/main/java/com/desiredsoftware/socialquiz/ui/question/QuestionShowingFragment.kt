package com.desiredsoftware.socialquiz.ui.question

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.question.GetQuestionCallback
import com.desiredsoftware.socialquiz.data.model.question.Question
import com.desiredsoftware.socialquiz.ui.components.AnswerVariantsAdapter
import com.desiredsoftware.socialquiz.ui.components.OnClickAnswerListener
import com.desiredsoftware.socialquiz.utils.convertToQuestion
import com.desiredsoftware.socialquiz.utils.generateMediaURI
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView

class QuestionShowingFragment : Fragment() {

    companion object {
        fun newInstance() = QuestionShowingFragment()
    }

    val args: QuestionShowingFragmentArgs by navArgs()

    private lateinit var viewModel: QuestionShowingViewModel

    lateinit var player : SimpleExoPlayer
    lateinit var playerControlView: StyledPlayerView
    lateinit var root : View
    lateinit var categoryName : String

    lateinit var questionType : String
    lateinit var currentQuestion : Question


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_question_showing, container, false)
        val framePlayerLayout : AspectRatioFrameLayout = root.findViewById(R.id.framePlayerLayout)
        framePlayerLayout.setAspectRatio(16f/9f)

        viewModel = QuestionShowingViewModel()
        viewModel.getNextQuestion(args.categoryName, object : GetQuestionCallback{
            override fun onCallback(questions: ArrayList<Any>) {
                if (questions!=null)
                // TODO : Delete this hardcode
                    currentQuestion = convertToQuestion(questions[0] as HashMap<String, Any>)

                val recyclerView : RecyclerView = root.findViewById(R.id.recyclerViewAnswerVariants)
                recyclerView.layoutManager = GridLayoutManager(requireContext(),1)
                recyclerView.adapter = AnswerVariantsAdapter(currentQuestion.answerVariants,
                        object : OnClickAnswerListener{
                            override fun onClicked(answerVariant: Question.Answer) {
                                // TODO: Show QuestionResultsFragment
                                val answerIsCorrect : Boolean = answerVariant.isCorrect
                                val action = QuestionShowingFragmentDirections.actionQuestionShowingFragmentToQuestionResultFragment(answerIsCorrect)
                                val navController = requireParentFragment().findNavController()
                                navController.navigate(action)
                            }

                        })

                if (currentQuestion.questionType.equals("video"))
                configurePlayer(currentQuestion.questionBody)
            }
        })

        return root
    }

    override fun onResume() {
        super.onResume()
        configurePlayer(generateMediaURI())
        Log.d("QuestionShowingFragment", "onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("QuestionShowingFragment", "onStop")
        releasePlayer()
    }

    override fun onDestroyView() {
        Log.d("QuestionShowingFragment", "onDestroyView")

        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("QuestionShowingFragment", "onDestroy")

        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("QuestionShowingFragment", "onDetach")
        super.onDetach()
    }

    fun configurePlayer(videoURI : String )
    {
        player = SimpleExoPlayer.Builder(requireContext()).build()
        playerControlView = root.findViewById(R.id.playerView)
        playerControlView.player = player
        player.setMediaItem(MediaItem.fromUri(videoURI))
        player.prepare()
    }

    fun releasePlayer()
    {
        player.release()
    }
}