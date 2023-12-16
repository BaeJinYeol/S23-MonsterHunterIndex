package kr.ac.kumoh.ce.s20190558.monsterhunterindex

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import coil.compose.AsyncImage
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kr.ac.kumoh.ce.s20190558.monsterhunterindex.ui.theme.MonsterHunterIndexTheme

enum class MonsterScreen {
    List,
    Detail
}

@Composable
fun MonsterApp(monsterList: List<Monster>) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MonsterScreen.List.name,
    ) {
        composable(route = MonsterScreen.List.name) {
            MonsterList(navController, monsterList)
        }
        composable(
            route = MonsterScreen.Detail.name + "/{index}",
            arguments = listOf(navArgument("index") {
                type = NavType.IntType
            })
        ) {
            val index = it.arguments?.getInt("index") ?: -1
            if (index >= 0)
                MonsterDetail(monsterList[index])
        }
    }
}

@Composable
fun MonsterList(navController: NavController, list: List<Monster>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(list.size) {
            MonsterItem(navController, list, it)
        }
    }
}
@Composable
fun MonsterItem(navController: NavController, monsterList: List<Monster>, index: Int) {

    Card(
        modifier = Modifier.clickable{
            navController.navigate(MonsterScreen.Detail.name + "/$index")
        },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(8.dp)
        ) {
            AsyncImage(
                model = monsterList[index].iconAddress,
                contentDescription = "${monsterList[index].name} 아이콘",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RectangleShape),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                TextName(monsterList[index].name)
                TextNickname(monsterList[index].nickname)
            }
        }
    }
}
@Composable
fun TextName(name: String){
    Text(name, fontSize = 30.sp, lineHeight = 35.sp,)
}
@Composable
fun TextNickname(nickname: String) {
    Text(nickname, fontSize = 20.sp)
}
@Composable
fun MonsterDetail(monster: Monster) {
    Column(
        modifier = Modifier
            .background(Color(252, 235, 255, 255))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            monster.name,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Text(
            monster.nickname,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            lineHeight = 25.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = monster.imageAddress,
            contentDescription = "${monster.name} 이미지",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(400.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            monster.species,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            lineHeight = 45.sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("약점: ${monster.weakness}", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "서식지: ${monster.maps}",
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            lineHeight = 35.sp,
        )
    }
}