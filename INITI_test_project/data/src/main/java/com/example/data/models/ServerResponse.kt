package com.example.data.models

import com.example.domain.models.Inet
import com.example.domain.models.Organization
import com.google.gson.annotations.SerializedName

data class ServerResponse(
    val objects: Objects?,
    @SerializedName("errormessages")
    val errorMessages: ErrorMessage?,
)

fun ServerResponse.toInet(): Inet =
    Inet(
        num =
            this.objects
                ?.model
                ?.first()
                ?.attributes
                ?.attribute
                ?.firstOrNull { it.name == "inetnum" }
                ?.value ?: "",
        name =
            this.objects
                ?.model
                ?.first()
                ?.attributes
                ?.attribute
                ?.firstOrNull { it.name == "netname" }
                ?.value ?: "",
        description =
            this.objects
                ?.model
                ?.first()
                ?.attributes
                ?.attribute
                ?.firstOrNull { it.name == "descr" }
                ?.value ?: "",
        country =
            this.objects
                ?.model
                ?.first()
                ?.attributes
                ?.attribute
                ?.firstOrNull { it.name == "country" }
                ?.value
                ?.uppercase(),
        orgId =
            this.objects
                ?.model
                ?.first()
                ?.attributes
                ?.attribute
                ?.firstOrNull { it.name == "org" }
                ?.value ?: "",
    )

fun ServerResponse.toInetList(): List<Inet> =

    this.objects
        ?.model
        ?.map { inet ->
            Inet(
                num =
                    inet
                        .attributes
                        .attribute
                        .firstOrNull { it.name == "inetnum" }
                        ?.value ?: "",
                name =
                    inet
                        .attributes
                        .attribute
                        .firstOrNull { it.name == "netname" }
                        ?.value ?: "",
                description =
                    inet
                        .attributes
                        .attribute
                        .firstOrNull { it.name == "descr" }
                        ?.value ?: "",
                country =
                    inet
                        .attributes
                        .attribute
                        .firstOrNull { it.name == "country" }
                        ?.value
                        ?.uppercase(),
                orgId =
                    inet
                        .attributes
                        .attribute
                        .firstOrNull { it.name == "org" }
                        ?.value ?: "",
            )
        } ?: emptyList()

fun ServerResponse.toOrganization(): List<Organization> {
    val orgList = mutableListOf<Organization>()
    objects?.model?.forEach { org ->
        orgList.add(
            Organization(
                orgId =
                    org.attributes.attribute
                        .firstOrNull { it.name == "organisation" }
                        ?.value ?: "",
                orgName =
                    org.attributes.attribute
                        .firstOrNull { it.name == "org-name" }
                        ?.value ?: "",
                orgCountry =
                    org.attributes.attribute
                        .firstOrNull { it.name == "country" }
                        ?.value
                        ?.uppercase() ?: "",
                address =
                    org.attributes.attribute
                        .filter { it.name == "address" }
                        .map { it.value }
                        .toString()
                        .replace("]", "")
                        .replace("[", ""),
            ),
        )
    }
    return orgList
}
