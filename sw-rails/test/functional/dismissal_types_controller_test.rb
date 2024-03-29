require 'test_helper'

class DismissalTypesControllerTest < ActionController::TestCase
  setup do
    @dismissal_type = dismissal_types(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:dismissal_types)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create dismissal_type" do
    assert_difference('DismissalType.count') do
      post :create, :dismissal_type => @dismissal_type.attributes
    end

    assert_redirected_to dismissal_type_path(assigns(:dismissal_type))
  end

  test "should show dismissal_type" do
    get :show, :id => @dismissal_type.to_param
    assert_response :success
  end

  test "should get edit" do
    get :edit, :id => @dismissal_type.to_param
    assert_response :success
  end

  test "should update dismissal_type" do
    put :update, :id => @dismissal_type.to_param, :dismissal_type => @dismissal_type.attributes
    assert_redirected_to dismissal_type_path(assigns(:dismissal_type))
  end

  test "should destroy dismissal_type" do
    assert_difference('DismissalType.count', -1) do
      delete :destroy, :id => @dismissal_type.to_param
    end

    assert_redirected_to dismissal_types_path
  end
end
