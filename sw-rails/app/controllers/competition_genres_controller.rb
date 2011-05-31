class CompetitionGenresController < ApplicationController
  # GET /competition_genres
  # GET /competition_genres.xml
  def index
    @competition_genres = CompetitionGenre.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @competition_genres }
    end
  end

  # GET /competition_genres/1
  # GET /competition_genres/1.xml
  def show
    @competition_genre = CompetitionGenre.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @competition_genre }
    end
  end

  # GET /competition_genres/new
  # GET /competition_genres/new.xml
  def new
    @competition_genre = CompetitionGenre.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @competition_genre }
    end
  end

  # GET /competition_genres/1/edit
  def edit
    @competition_genre = CompetitionGenre.find(params[:id])
  end

  # POST /competition_genres
  # POST /competition_genres.xml
  def create
    @competition_genre = CompetitionGenre.new(params[:competition_genre])

    respond_to do |format|
      if @competition_genre.save
        format.html { redirect_to(@competition_genre, :notice => 'Competition genre was successfully created.') }
        format.xml  { render :xml => @competition_genre, :status => :created, :location => @competition_genre }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @competition_genre.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /competition_genres/1
  # PUT /competition_genres/1.xml
  def update
    @competition_genre = CompetitionGenre.find(params[:id])

    respond_to do |format|
      if @competition_genre.update_attributes(params[:competition_genre])
        format.html { redirect_to(@competition_genre, :notice => 'Competition genre was successfully updated.') }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @competition_genre.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /competition_genres/1
  # DELETE /competition_genres/1.xml
  def destroy
    @competition_genre = CompetitionGenre.find(params[:id])
    @competition_genre.destroy

    respond_to do |format|
      format.html { redirect_to(competition_genres_url) }
      format.xml  { head :ok }
    end
  end
end
