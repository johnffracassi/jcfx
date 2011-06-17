class SpritesController < ApplicationController

  def index
    headers["Content-Type"] = "text/javascript"
    render 'sprites/index.js'
  end

end
