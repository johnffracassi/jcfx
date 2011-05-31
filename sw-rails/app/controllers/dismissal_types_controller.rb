class DismissalTypesController < ApplicationController
  # GET /dismissal_types
  # GET /dismissal_types.xml
  def index
    @dismissal_types = DismissalType.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @dismissal_types }
    end
  end

  # GET /dismissal_types/1
  # GET /dismissal_types/1.xml
  def show
    @dismissal_type = DismissalType.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @dismissal_type }
    end
  end

  # GET /dismissal_types/new
  # GET /dismissal_types/new.xml
  def new
    @dismissal_type = DismissalType.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @dismissal_type }
    end
  end

  # GET /dismissal_types/1/edit
  def edit
    @dismissal_type = DismissalType.find(params[:id])
  end

  # POST /dismissal_types
  # POST /dismissal_types.xml
  def create
    @dismissal_type = DismissalType.new(params[:dismissal_type])

    respond_to do |format|
      if @dismissal_type.save
        format.html { redirect_to(@dismissal_type, :notice => 'Dismissal type was successfully created.') }
        format.xml  { render :xml => @dismissal_type, :status => :created, :location => @dismissal_type }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @dismissal_type.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /dismissal_types/1
  # PUT /dismissal_types/1.xml
  def update
    @dismissal_type = DismissalType.find(params[:id])

    respond_to do |format|
      if @dismissal_type.update_attributes(params[:dismissal_type])
        format.html { redirect_to(@dismissal_type, :notice => 'Dismissal type was successfully updated.') }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @dismissal_type.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /dismissal_types/1
  # DELETE /dismissal_types/1.xml
  def destroy
    @dismissal_type = DismissalType.find(params[:id])
    @dismissal_type.destroy

    respond_to do |format|
      format.html { redirect_to(dismissal_types_url) }
      format.xml  { head :ok }
    end
  end
end
