package com.desiredsoftware.socialquiz.ui.question

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bluelinelabs.conductor.RouterTransaction
import com.desiredsoftware.socialquiz.R
import com.desiredsoftware.socialquiz.data.model.question.Answer
import com.desiredsoftware.socialquiz.databinding.ViewControllerQuestionShowingBinding
import com.desiredsoftware.socialquiz.di.App
import com.desiredsoftware.socialquiz.presenter.question.QuestionShowingPresenter
import com.desiredsoftware.socialquiz.ui.categories.CategoriesController.Companion.CATEGORY_ID_KEY
import com.desiredsoftware.socialquiz.ui.common.MvpController
import com.desiredsoftware.socialquiz.ui.components.AnswersAdapter
import com.desiredsoftware.socialquiz.ui.components.OnClickAnswerListener
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject


class QuestionShowingController : MvpController, QuestionShowingPresenter.IQuestionView {
    constructor() : super()
    constructor (args: Bundle) : super(args)

    private var player: SimpleExoPlayer? = null
    lateinit var mAdapter: AnswersAdapter

    private var _binding: ViewControllerQuestionShowingBinding? = null
    private val binding get() = _binding!!

    @Inject
    @InjectPresenter
    lateinit var presenter: QuestionShowingPresenter

    @ProvidePresenter
    fun provides() = presenter

    override fun inject() {
        super.inject()
        App.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
        savedViewState: Bundle?
    ): View {
        _binding = ViewControllerQuestionShowingBinding.inflate(inflater, container, false)

        presenter.questionCategoryId = args.getString(CATEGORY_ID_KEY).toString()
        presenter.initUI()

        return binding.root
    }

    private fun configurePlayer(context: Context, videoURI: String) {
        if (player == null) {
            player = SimpleExoPlayer.Builder(context)
                .build()

            binding.playerView.player = player
            player?.let { player ->
                player.setMediaItem(MediaItem.fromUri(videoURI))
                player.prepare()
                player.playWhenReady = true
                player.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        when (state) {
                            Player.STATE_ENDED -> {
                                binding.listAnswers.visibility = View.VISIBLE
                                binding.buttonRepeatVideo.apply {
                                    visibility = View.VISIBLE
                                    this.setOnClickListener {
                                        player?.let { player ->
                                            // Replay video
                                            player.seekTo(0)
                                            player.playWhenReady = true
                                            this.visibility = View.INVISIBLE
                                            binding.listAnswers.visibility = View.INVISIBLE
                                        }
                                    }
                                }
                            }
                        }
                    }

                    override fun onPlayerError(error: ExoPlaybackException) {
                        super.onPlayerError(error)
                        Toast.makeText(activity, activity?.resources?.getString(R.string.playback_error), Toast.LENGTH_LONG).show()
                        error.printStackTrace()
                        binding.listAnswers.visibility = View.VISIBLE
                    }
                })
            }
        }
        else{
            player?.seekTo(0)
            player?.playWhenReady = true
        }
    }

    override fun showVideoQuestion(context: Context, questionBodyVideoUri: String) {
        configurePlayer(context, questionBodyVideoUri)
    }

    override fun showTextQuestion(context: Context, questionBodyVideoUri: String) {
        // TODO("Not yet implemented")
    }

    override fun popCurrentController() {
        router.popCurrentController()
    }

    override fun showAnswers(context: Context, answers: List<Answer>) {
        mAdapter = AnswersAdapter(answersList = answers, object : OnClickAnswerListener {
            override fun onClicked(answer: Answer) {
                val bundle = Bundle()
                bundle.putSerializable(ANSWER_IS_CORRECT_KEY, answer.isCorrect)
                if (answer.isCorrect){
                    presenter.saveSolvedQuestion()
                }
                val transaction = RouterTransaction.with(QuestionResultController(bundle))
                router.replaceTopController(transaction)
            }
        })

        binding.listAnswers.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun showError(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        player?.stop()
        player?.release()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        if (player != null) {
            player?.release()
            player = null
        }
    }

    companion object {
        val ANSWER_IS_CORRECT_KEY = "ANSWER_IS_CORRECT_KEY"
    }
}