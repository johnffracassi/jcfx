require 'test_helper'

class PlayerAliasesControllerTest < ActionController::TestCase
  setup do
    @player_alias = player_aliases(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:player_aliases)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create player_alias" do
    assert_difference('PlayerAlias.count') do
      post :create, :player_alias => @player_alias.attributes
    end

    assert_redirected_to player_alias_path(assigns(:player_alias))
  end

  test "should show player_alias" do
    get :show, :id => @player_alias.to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => @player_alias.to_param
    assert_response :success
  end

  test "should update player_alias" do
    put :update, :id => @player_alias.to_param, :player_alias => @player_alias.attributes
    assert_redirected_to player_alias_path(assigns(:player_alias))
  end

  test "should destroy player_alias" do
    assert_difference('PlayerAlias.count', -1) do
      delete :destroy, :id => @player_alias.to_param
    end

    assert_redirected_to player_aliases_path
  end
end
