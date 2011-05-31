module ApplicationHelper

  def ball_td(ball)
    unless ball.nil?
      cls = ball.dismissal_type.nil? ? (ball.extra_type.nil? ? ball.score : "X") : "W"
      content_tag("td", ball.score, {:class => "ball score-#{cls}"})
    else
      content_tag("td", "", {:class => "ball"})
    end
  end

  def inns_table(caption, inns)
    @caption = caption
    @inns = inns
    render "profiles/inns_table"
  end

end
