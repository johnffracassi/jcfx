class ExtraTypesController < ApplicationController
  # GET /extra_types
  # GET /extra_types.xml
  def index
    @extra_types = ExtraType.all

    respond_to do |format|
      format.html # index.html.erb
      format.xml  { render :xml => @extra_types }
    end
  end

  # GET /extra_types/1
  # GET /extra_types/1.xml
  def show
    @extra_type = ExtraType.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.xml  { render :xml => @extra_type }
    end
  end

  # GET /extra_types/new
  # GET /extra_types/new.xml
  def new
    @extra_type = ExtraType.new

    respond_to do |format|
      format.html # new.html.erb
      format.xml  { render :xml => @extra_type }
    end
  end

  # GET /extra_types/1/edit
  def edit
    @extra_type = ExtraType.find(params[:id])
  end

  # POST /extra_types
  # POST /extra_types.xml
  def create
    @extra_type = ExtraType.new(params[:extra_type])

    respond_to do |format|
      if @extra_type.save
        format.html { redirect_to(@extra_type, :notice => 'Extra type was successfully created.') }
        format.xml  { render :xml => @extra_type, :status => :created, :location => @extra_type }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @extra_type.errors, :status => :unprocessable_entity }
      end
    end
  end

  # PUT /extra_types/1
  # PUT /extra_types/1.xml
  def update
    @extra_type = ExtraType.find(params[:id])

    respond_to do |format|
      if @extra_type.update_attributes(params[:extra_type])
        format.html { redirect_to(@extra_type, :notice => 'Extra type was successfully updated.') }
        format.xml  { head :ok }
      else
        format.html { render :action => "edit" }
        format.xml  { render :xml => @extra_type.errors, :status => :unprocessable_entity }
      end
    end
  end

  # DELETE /extra_types/1
  # DELETE /extra_types/1.xml
  def destroy
    @extra_type = ExtraType.find(params[:id])
    @extra_type.destroy

    respond_to do |format|
      format.html { redirect_to(extra_types_url) }
      format.xml  { head :ok }
    end
  end
end
