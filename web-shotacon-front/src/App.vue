<template>
  <el-container>
    <!--<el-header>-->
      <nav class="el-header navbar navbar-expand-lg navbar-light bg-light" role="navigation">
        <div class="container-fluid">
          <div class="navbar-header">
            <!--<img height="5%" width="5%" src="data:image/svg+xml;base64,PHN2ZyBhcmlhLWhpZGRlbj0idHJ1ZSIgZGF0YS1wcmVmaXg9ImZhcyIgZGF0YS1pY29uPSJtYXJzIiBjbGFzcz0ic3ZnLWlubGluZS0tZmEgZmEtbWFycyBmYS13LTEyIiByb2xlPSJpbWciIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgdmlld0JveD0iMCAwIDM4NCA1MTIiPjxwYXRoIGZpbGw9ImN1cnJlbnRDb2xvciIgZD0iTTM3MiA2NGgtNzljLTEwLjcgMC0xNiAxMi45LTguNSAyMC41bDE2LjkgMTYuOS04MC43IDgwLjdjLTIyLjItMTQtNDguNS0yMi4xLTc2LjctMjIuMUM2NC41IDE2MCAwIDIyNC41IDAgMzA0czY0LjUgMTQ0IDE0NCAxNDQgMTQ0LTY0LjUgMTQ0LTE0NGMwLTI4LjItOC4xLTU0LjUtMjIuMS03Ni43bDgwLjctODAuNyAxNi45IDE2LjljNy42IDcuNiAyMC41IDIuMiAyMC41LTguNVY3NmMwLTYuNi01LjQtMTItMTItMTJ6TTE0NCAzODRjLTQ0LjEgMC04MC0zNS45LTgwLTgwczM1LjktODAgODAtODAgODAgMzUuOSA4MCA4MC0zNS45IDgwLTgwIDgweiI+PC9wYXRoPjwvc3ZnPg==">-->
            <a class="navbar-brand" href="#">Boy ♂ Next Door</a>
          </div>
          <div class="collapse navbar-collapse">
            <ul class="navbar-nav mr-auto">
              <li class="nav-item active"><a class="nav-link" href="#">Fuck ♂ou</a></li>
              <li class="nav-item"><a class="nav-link" href="#">VAN♂様</a></li>
              <li class="nav-item dropdown">
                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">
                  Deep♂
                  <b class="caret"></b>
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                  <a class="dropdown-item" href="#">Dark</a>
                  <a class="dropdown-item" href="#">Fantasy</a>
                </div>
              </li>
            </ul>
          </div>
        </div>
      </nav>
    <!--</el-header>-->

    <el-main>

    </el-main>

    <el-footer>
      Copyright © Shotacon
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }

  .el-footer, .el-header{
    text-align: center;
    position: absolute;
    left: 0;
    right: 0;
  }

  .el-header{
    background-color: #f8f9fa;
    line-height: 30px;
    top: 0;
  }

  .el-footer {
    bottom: 0;
    line-height: 80px;
    height: 30px;
  }

  .el-main {
    /*background-color: #E9EEF3;*/
    color: #333;
    /*text-align: center;*/
    line-height: 30px;
    position: absolute;
    left: 0;
    right: 0;
    top: 60px;
    bottom: 60px;
  }

</style>
