package com.example.data.models

import com.example.data.database.models.InetDB
import com.example.data.database.models.OrganizationDB
import com.example.domain.models.Inet
import com.example.domain.models.Organization
import retrofit2.HttpException

fun HttpException.toErrorText(searchText: String): String =
    when (this.code()) {
        404 -> {
            "Поиск информации по \"$searchText\" не дал результатов.\nВведите корректные данные"
        }
        400 -> {
            "Поиск информации по \"$searchText\" не дал результатов.\nВведите корректные данные"
        }
        else -> {
            "${this.code()} Error: ${this.message()}"
        }
    }

fun Inet.toInetDB(): InetDB =
    InetDB(
        num = this.num,
        name = this.name,
        description = this.description,
        country = this.country,
        orgId = this.orgId,
        ipMask = getIpMask(this.num),
        ipMin = getIpMin(this.num),
        ipMax = getIpMax(this.num),
    )

private fun getIpMask(ipNum: String): String {
    val parseList = ipNum.split(".")
    return "${parseList[0]}.${parseList[1]}"
}

private fun getIpMin(ipNum: String): Int {
    val parseList = ipNum.split(".")
    return parseList[2].toInt()
}

private fun getIpMax(ipNum: String): Int {
    val parseList = ipNum.split(".")
    return parseList[5].toInt()
}

fun Organization.toOrganizationDB() =
    OrganizationDB(
        id = this.orgId,
        name = this.orgName,
        country = this.orgCountry,
        address = this.address,
    )

fun InetDB.toInet() =
    Inet(
        num = this.num,
        name = this.name,
        description = this.description,
        country = this.country,
        orgId = this.orgId,
    )

fun OrganizationDB.toOrganization() =
    Organization(
        orgId = this.id,
        orgName = this.name,
        orgCountry = this.country,
        address = this.address,
    )
