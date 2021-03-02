package com.medplus.webservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.medplus.adapter.interfaces.SearchPresenter;
import com.medplus.gateways.Cloner;
import com.medplus.entities.domain.HealthProvider;
import com.medplus.entities.provider.impl.Provider;
import com.medplus.entities.provider.filter.ProviderFilter;
import com.medplus.entities.provider.filter.ProviderFilterParameter;
import com.medplus.factories.ProviderFilterFactoryImpl;
import com.medplus.factories.TestUtils;
import com.medplus.useCases.search.ProviderFilterParameterImpl;
import com.medplus.useCases.search.SearchUseCase;
import com.medplus.webservice.adapter.ProviderGWDatabaseAdapter;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;

@Validated
@Controller ("/provider")
public class ProviderController
{
    private final SearchUseCase useCase;
    private final SearchPresenter presenter;

    ProviderController(ProviderGWDatabaseAdapter providerGw)
    {
        ProviderFilter filter = (new ProviderFilterFactoryImpl()).Make();
        filter.setPicker(TestUtils.mountPickerChain());
        presenter = new SearchPresenter();
        useCase = new SearchUseCase(providerGw, filter, presenter);
    }

    @Get ()
    public HttpResponse<List<Provider>> getAll()
    {
        useCase.search(null);

        List<Provider> providersImpl = getSearchResult();

        return HttpResponse.status(HttpStatus.OK).body(providersImpl);
    }

    @Post ("search")
    public HttpResponse<?> post(@Body ProviderFilterParameterImpl filterParam)
    {
        useCase.search(filterParam);

        List<Provider> providersImpl = getSearchResult();

        return HttpResponse.status(HttpStatus.OK).body(providersImpl);
    }

    private List<Provider> getSearchResult()
    {
        ArrayList<HealthProvider> providersInt = presenter.getProviders();

        providersInt = providersInt != null ? providersInt : new ArrayList<>();

        return providersInt.stream().map(p -> (Provider) Cloner.cloneProvider(p)).collect(Collectors.toList());
    }
}
